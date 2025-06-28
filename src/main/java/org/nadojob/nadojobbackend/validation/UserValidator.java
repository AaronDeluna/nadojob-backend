package org.nadojob.nadojobbackend.validation;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.exception.PasswordNotCorrectException;
import org.nadojob.nadojobbackend.exception.UserBlockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final PasswordEncoder passwordEncoder;

    public void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new PasswordNotCorrectException("Неверный пароль");
        }
    }

    public void checkIfBlocked(User user) {
        if (user.getIsBlocked()) {
            throw new UserBlockedException("Ваш аккаунт заблокирован");
        }
    }

}

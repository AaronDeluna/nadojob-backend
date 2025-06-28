package org.nadojob.nadojobbackend.validation;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.exception.EmailAlreadyExistsException;
import org.nadojob.nadojobbackend.exception.PasswordNotCorrectException;
import org.nadojob.nadojobbackend.exception.PhoneAlreadyExistsException;
import org.nadojob.nadojobbackend.exception.UserBlockedException;
import org.nadojob.nadojobbackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

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

    public void validateEmailDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Пользователь с такой почтой уже зарегистрирован");
        }
    }

    public void validatePhoneDuplicate(String phone) {
        if (userRepository.existsByPhone(phone)) {
            throw new PhoneAlreadyExistsException("Пользователь с таким номером уже зарегистрирован");
        }
    }

}

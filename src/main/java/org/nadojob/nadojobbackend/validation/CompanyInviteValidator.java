package org.nadojob.nadojobbackend.validation;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.entity.CompanyInvite;
import org.nadojob.nadojobbackend.exception.InviteTokenExpiredException;
import org.nadojob.nadojobbackend.exception.UserAlreadyInvitedException;
import org.nadojob.nadojobbackend.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CompanyInviteValidator {

    private final UserRepository userRepository;

    public void validateEmailNotExists(String inviteEmail) {
        if (userRepository.existsByEmail(inviteEmail)) {
            throw new UserAlreadyInvitedException(
                    "Пользователь уже зарегистрирован в системе и не может быть приглашен в компанию."
                            + " Пожалуйста, обратитесь в поддержку.");
        }
    }

    public void validateInviteToken(CompanyInvite companyInvite) {
        if (companyInvite.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InviteTokenExpiredException(
                    "Срок действия приглашения истёк. Для продолжения получите новое приглашение.");
        }
    }

}

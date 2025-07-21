package org.nadojob.nadojobbackend.validation.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nadojob.nadojobbackend.entity.CompanyInvite;
import org.nadojob.nadojobbackend.exception.InviteTokenExpiredException;
import org.nadojob.nadojobbackend.exception.UserAlreadyInvitedException;
import org.nadojob.nadojobbackend.repository.UserRepository;
import org.nadojob.nadojobbackend.validation.CompanyInviteValidator;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyInviteValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CompanyInviteValidator companyInviteValidator;

    @Test
    @DisplayName("Не выбрасывает, если email отсутствует в системе")
    void emailNotExists() {
        when(userRepository.existsByEmail("test@mail.com")).thenReturn(false);
        assertDoesNotThrow(() -> companyInviteValidator.validateEmailNotExists("test@mail.com"));
    }

    @Test
    @DisplayName("Выбрасывает, если email уже зарегистрирован")
    void emailAlreadyExists() {
        when(userRepository.existsByEmail("test@mail.com")).thenReturn(true);
        assertThrows(UserAlreadyInvitedException.class,
                () -> companyInviteValidator.validateEmailNotExists("test@mail.com"));
    }

    @Test
    @DisplayName("Не выбрасывает, если токен приглашения ещё активен")
    void inviteTokenValid() {
        CompanyInvite invite = CompanyInvite.builder()
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .build();
        assertDoesNotThrow(() -> companyInviteValidator.validateInviteToken(invite));
    }

    @Test
    @DisplayName("Выбрасывает, если токен приглашения истёк")
    void inviteTokenExpired() {
        CompanyInvite invite = CompanyInvite.builder()
                .expiresAt(LocalDateTime.now().minusMinutes(1))
                .build();
        assertThrows(InviteTokenExpiredException.class,
                () -> companyInviteValidator.validateInviteToken(invite));
    }
}


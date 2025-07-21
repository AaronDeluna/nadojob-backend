package org.nadojob.nadojobbackend.validation.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.entity.UserRole;
import org.nadojob.nadojobbackend.exception.EmailAlreadyExistsException;
import org.nadojob.nadojobbackend.exception.PasswordNotCorrectException;
import org.nadojob.nadojobbackend.exception.PhoneAlreadyExistsException;
import org.nadojob.nadojobbackend.exception.UserBlockedException;
import org.nadojob.nadojobbackend.repository.UserRepository;
import org.nadojob.nadojobbackend.validation.UserValidator;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit-тесты для UserValidator")
class UserValidatorTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserValidator userValidator;

    @Test
    @DisplayName("Не выбрасывает, если пароль совпадает")
    void validPassword() {
        when(passwordEncoder.matches("raw", "encode")).thenReturn(true);
        assertDoesNotThrow(() -> userValidator.validatePassword("raw", "encode"));
    }

    @Test
    @DisplayName("Выбрасывает, если пароль не совпадает")
    void invalidPassword() {
        when(passwordEncoder.matches("raw", "encode")).thenReturn(false);
        assertThrows(PasswordNotCorrectException.class,
                () -> userValidator.validatePassword("raw", "encode"));
    }

    @Test
    @DisplayName("Выбрасывает, если пользователь заблокирован")
    void blockedUser() {
        User user = new User(
                UUID.randomUUID(),
                UserRole.CANDIDATE,
                List.of(),
                "aa@mail.ru",
                "hashedPassword",
                "12345",
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        assertThrows(UserBlockedException.class, () -> userValidator.checkIfBlocked(user));
    }

    @Test
    @DisplayName("Не выбрасывает, если пользователь не заблокирован")
    void activeUser() {
        User user = new User(
                UUID.randomUUID(),
                UserRole.CANDIDATE,
                List.of(),
                "aa@mail.ru",
                "hashedPassword",
                "12345",
                false,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        assertDoesNotThrow(() -> userValidator.checkIfBlocked(user));
    }

    @Test
    @DisplayName("Не выбрасывает, если email уникален")
    void uniqueEmail() {
        when(userRepository.existsByEmail("test@mail.com")).thenReturn(false);
        assertDoesNotThrow(() -> userValidator.validateEmailDuplicate("test@mail.com"));
    }

    @Test
    @DisplayName("Выбрасывает, если email уже существует")
    void duplicateEmail() {
        when(userRepository.existsByEmail("test@mail.com")).thenReturn(true);
        assertThrows(EmailAlreadyExistsException.class,
                () -> userValidator.validateEmailDuplicate("test@mail.com"));
    }

    @Test
    @DisplayName("Не выбрасывает, если телефон уникален")
    void uniquePhone() {
        when(userRepository.existsByPhone("+000000000")).thenReturn(false);
        assertDoesNotThrow(() -> userValidator.validatePhoneDuplicate("+000000000"));
    }

    @Test
    @DisplayName("Выбрасывает, если телефон уже существует")
    void duplicatePhone() {
        when(userRepository.existsByPhone("+000000000")).thenReturn(true);
        assertThrows(PhoneAlreadyExistsException.class,
                () -> userValidator.validatePhoneDuplicate("+000000000"));
    }

}

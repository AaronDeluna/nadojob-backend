package org.nadojob.nadojobbackend.validation.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nadojob.nadojobbackend.exception.ProfileTitleAlreadyExistsException;
import org.nadojob.nadojobbackend.repository.CandidateProfileRepository;
import org.nadojob.nadojobbackend.validation.CandidateProfileValidator;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CandidateProfileValidatorTest {

    @Mock
    private CandidateProfileRepository candidateProfileRepository;

    @InjectMocks
    private CandidateProfileValidator candidateProfileValidator;

    @Test
    @DisplayName("Не выбрасывает, если название профиля уникально для пользователя")
    void uniqueTitle() {
        UUID userId = UUID.randomUUID();
        when(candidateProfileRepository.existsByTitleAndUserId("Java Developer", userId)).thenReturn(false);
        assertDoesNotThrow(() -> candidateProfileValidator.validateTitleUniqueness("Java Developer", userId));
    }

    @Test
    @DisplayName("Выбрасывает, если название профиля уже используется пользователем")
    void duplicateTitle() {
        UUID userId = UUID.randomUUID();
        when(candidateProfileRepository.existsByTitleAndUserId("Java Developer", userId)).thenReturn(true);
        assertThrows(ProfileTitleAlreadyExistsException.class,
                () -> candidateProfileValidator.validateTitleUniqueness("Java Developer", userId));
    }
}


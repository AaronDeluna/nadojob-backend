package org.nadojob.nadojobbackend.validation.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nadojob.nadojobbackend.exception.JobApplicationAlreadyExistsException;
import org.nadojob.nadojobbackend.repository.JobApplicationRepository;
import org.nadojob.nadojobbackend.validation.JobPostValidator;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobPostValidatorTest {

    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @InjectMocks
    private JobPostValidator jobPostValidator;

    @Test
    @DisplayName("Не выбрасывает, если кандидат не откликался")
    void noDuplicateApplication() {
        UUID candidateId = UUID.randomUUID();
        UUID jobPostId = UUID.randomUUID();

        when(jobApplicationRepository.existsByCandidateIdAndJobPostId(candidateId, jobPostId))
                .thenReturn(false);

        assertDoesNotThrow(() -> jobPostValidator.validateDuplicateApply(candidateId, jobPostId));
    }

    @Test
    @DisplayName("Выбрасывает, если кандидат уже откликался")
    void duplicateApplication() {
        UUID candidateId = UUID.randomUUID();
        UUID jobPostId = UUID.randomUUID();

        when(jobApplicationRepository.existsByCandidateIdAndJobPostId(candidateId, jobPostId))
                .thenReturn(true);

        assertThrows(JobApplicationAlreadyExistsException.class,
                () -> jobPostValidator.validateDuplicateApply(candidateId, jobPostId));
    }
}

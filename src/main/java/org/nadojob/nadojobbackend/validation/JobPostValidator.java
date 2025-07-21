package org.nadojob.nadojobbackend.validation;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.exception.DailyApplicationLimitExceededException;
import org.nadojob.nadojobbackend.exception.JobApplicationAlreadyExistsException;
import org.nadojob.nadojobbackend.repository.JobApplicationRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JobPostValidator {

    private final static int MAX_APPLICATION_PER_DAY = 200;
    private final JobApplicationRepository jobApplicationRepository;

    public void validateDuplicateApply(UUID candidateProfileId, UUID jobPostId) {
        if (jobApplicationRepository.existsByCandidateIdAndJobPostId(candidateProfileId, jobPostId)) {
            throw new JobApplicationAlreadyExistsException("Вы уже откликались на эту вакансию.");
        }
    }

    public void validateMaxApplicationsPerDay(UUID candidateId) {
        if (jobApplicationRepository.countByCandidateIdAndCreatedDate(candidateId, LocalDate.now())
                >= MAX_APPLICATION_PER_DAY) {
            throw new DailyApplicationLimitExceededException("Достигнут лимит откликов за сегодня.");
        }
    }

}

package org.nadojob.nadojobbackend.validation;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.exception.JobApplicationAlreadyExistsException;
import org.nadojob.nadojobbackend.repository.JobApplicationRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JobPostValidator {

    private final JobApplicationRepository jobApplicationRepository;

    public void validateDuplicateApply(UUID candidateProfileId, UUID jobPostId) {
        if (jobApplicationRepository.existsByCandidateIdAndJobPostId(candidateProfileId, jobPostId)) {
            throw new JobApplicationAlreadyExistsException("Вы уже откликались на эту вакансию.");
        }
    }

}

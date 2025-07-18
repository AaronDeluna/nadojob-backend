package org.nadojob.nadojobbackend.service.job_post;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.entity.Application;
import org.nadojob.nadojobbackend.entity.CandidateProfile;
import org.nadojob.nadojobbackend.entity.JobPost;
import org.nadojob.nadojobbackend.exception.JobApplicationAlreadyExistsException;
import org.nadojob.nadojobbackend.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    public void create(CandidateProfile candidateProfile, JobPost jobPost, BigDecimal score) {
        jobApplicationRepository.save(Application.builder()
                .jobPost(jobPost)
                .candidate(candidateProfile)
                .suitabilityScore(score)
                .build());
    }

}

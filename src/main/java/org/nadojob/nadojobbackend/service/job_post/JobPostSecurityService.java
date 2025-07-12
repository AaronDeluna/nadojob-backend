package org.nadojob.nadojobbackend.service.job_post;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.repository.JobPostRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service(value = "jobPostSecurityService")
@RequiredArgsConstructor
public class JobPostSecurityService {

    private final JobPostRepository jobPostRepository;

    public boolean isUserEmployerOfJobPost(UUID jobPostId, UUID userId) {
        return jobPostRepository.existsByIdAndCompanyEmployees_Id(jobPostId, userId);
    }

}

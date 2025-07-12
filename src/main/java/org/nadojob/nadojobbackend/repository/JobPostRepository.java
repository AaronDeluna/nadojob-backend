package org.nadojob.nadojobbackend.repository;

import org.nadojob.nadojobbackend.entity.JobPost;
import org.nadojob.nadojobbackend.entity.JobPostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobPostRepository extends JpaRepository<JobPost, UUID> {
    Page<JobPost> findByStatus(Pageable pageable, JobPostStatus status);

    boolean existsByIdAndCompanyEmployees_Id(UUID jobId, UUID userId);
}

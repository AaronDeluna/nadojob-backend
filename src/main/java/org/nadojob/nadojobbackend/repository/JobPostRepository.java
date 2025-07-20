package org.nadojob.nadojobbackend.repository;

import org.nadojob.nadojobbackend.entity.JobPost;
import org.nadojob.nadojobbackend.entity.JobPostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JobPostRepository extends JpaRepository<JobPost, UUID> {
    List<JobPost> findAllByCompanyId(UUID companyId);
    Page<JobPost> findByStatus(Pageable pageable, JobPostStatus status);

    boolean existsByIdAndCompanyEmployees_Id(UUID jobPostId, UUID userId);

}

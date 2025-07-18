package org.nadojob.nadojobbackend.repository;

import org.nadojob.nadojobbackend.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobApplicationRepository extends JpaRepository<Application, UUID> {
    boolean existsByCandidateIdAndJobPostId(UUID candidateId, UUID jobPostId);
}

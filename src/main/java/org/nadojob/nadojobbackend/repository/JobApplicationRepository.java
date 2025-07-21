package org.nadojob.nadojobbackend.repository;

import org.nadojob.nadojobbackend.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface JobApplicationRepository extends JpaRepository<Application, UUID> {
    Boolean existsByCandidateIdAndJobPostId(UUID candidateId, UUID jobPostId);
    @Query("""
            SELECT COUNT(a)
            FROM Application a
            WHERE a.candidate.id = :candidateId
            AND CAST(a.createdAt AS date) = :today
            """)
    Integer countByCandidateIdAndCreatedDate(@Param("candidateId") UUID candidateId,
                                             @Param("today") LocalDate today);

}

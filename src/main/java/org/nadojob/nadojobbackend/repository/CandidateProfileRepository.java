package org.nadojob.nadojobbackend.repository;

import org.nadojob.nadojobbackend.entity.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, UUID> {

    @Query("""
            SELECT COUNT(cp) > 0
            FROM CandidateProfile cp
            WHERE cp.title = :title
            AND cp.user.id = :userId
            """)
    Boolean existsByTitleAndUserId(@Param("title") String title, @Param("userId") UUID userId);

    List<CandidateProfile> findAllByUserId(UUID userID);

    Optional<CandidateProfile> findByIdAndUserId(UUID candidateProfileId, UUID userId);

}

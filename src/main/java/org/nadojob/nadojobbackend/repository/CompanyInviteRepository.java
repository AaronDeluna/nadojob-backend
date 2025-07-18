package org.nadojob.nadojobbackend.repository;

import org.nadojob.nadojobbackend.entity.CompanyInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyInviteRepository extends JpaRepository<CompanyInvite, UUID> {
    Optional<CompanyInvite> findByEmail(String email);

    Optional<CompanyInvite> findByToken(String token);
}

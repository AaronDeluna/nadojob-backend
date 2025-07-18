package org.nadojob.nadojobbackend.repository;

import org.nadojob.nadojobbackend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    Optional<Company> findByCreatedById(UUID userId);
    Boolean existsByName(String companyName);

}

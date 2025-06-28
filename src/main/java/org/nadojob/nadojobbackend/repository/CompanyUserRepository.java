package org.nadojob.nadojobbackend.repository;

import org.nadojob.nadojobbackend.entity.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, UUID> {
}

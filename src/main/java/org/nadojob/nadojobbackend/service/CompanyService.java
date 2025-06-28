package org.nadojob.nadojobbackend.service;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.dto.auth.employer.EmployerRegistrationRequestDto;
import org.nadojob.nadojobbackend.entity.Company;
import org.nadojob.nadojobbackend.entity.CompanyUser;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.mapper.CompanyMapper;
import org.nadojob.nadojobbackend.repository.CompanyRepository;
import org.nadojob.nadojobbackend.repository.CompanyUserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyUserRepository companyUserRepository;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public Company create(EmployerRegistrationRequestDto dto, User employer) {
        Company saved = companyRepository.save(companyMapper.toEntity(dto));

        CompanyUser companyUser = new CompanyUser();
        companyUser.setUser(employer);
        companyUser.setCompany(saved);
        companyUserRepository.save(companyUser);

        return saved;
    }

}

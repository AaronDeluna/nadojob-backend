package org.nadojob.nadojobbackend.service;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.dto.auth.employer.EmployerRegistrationRequestDto;
import org.nadojob.nadojobbackend.entity.Company;
import org.nadojob.nadojobbackend.exception.EmailAlreadyExistsException;
import org.nadojob.nadojobbackend.mapper.CompanyMapper;
import org.nadojob.nadojobbackend.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public Company create(EmployerRegistrationRequestDto dto) {
        validateCompanyNameDuplicate(dto.getName());
        return companyRepository.save(companyMapper.toEntity(dto));
    }

    private void validateCompanyNameDuplicate(String companyName) {
        if (companyRepository.existsByName(companyName)) {
            throw new EmailAlreadyExistsException("Компания с таким название уже сущестует");
        }
    }

}

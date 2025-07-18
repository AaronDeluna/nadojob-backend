package org.nadojob.nadojobbackend.validation;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.exception.CompanyNameAlreadyExistsException;
import org.nadojob.nadojobbackend.repository.CompanyRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyValidator {

    private final CompanyRepository companyRepository;

    public void validateCompanyNameDuplicate(String companyName) {
        if (companyRepository.existsByName(companyName)) {
            throw new CompanyNameAlreadyExistsException("Компания с таким название уже сущестует");
        }
    }

}

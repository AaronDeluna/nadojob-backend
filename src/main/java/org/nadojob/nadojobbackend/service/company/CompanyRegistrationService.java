package org.nadojob.nadojobbackend.service.company;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.dto.CompanyCreationDto;
import org.nadojob.nadojobbackend.dto.auth.employer.EmployerRegistrationRequestDto;
import org.nadojob.nadojobbackend.entity.Company;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.mapper.CompanyMapper;
import org.nadojob.nadojobbackend.service.UserService;
import org.nadojob.nadojobbackend.validation.UserValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyRegistrationService {

    private final UserService userService;
    private final CompanyService companyService;
    private final CompanyUserService companyUserService;
    private final CompanyMapper companyMapper;

    @Transactional
    public User registerCompanyWithOwner(EmployerRegistrationRequestDto dto) {
        User user = userService.createEmployer(dto);
        Company company = companyService.create(companyMapper.toCreationDto(dto));
        companyUserService.create(company, user);
        return user;
    }

}

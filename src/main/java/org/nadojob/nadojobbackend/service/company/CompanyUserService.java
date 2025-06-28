package org.nadojob.nadojobbackend.service.company;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.entity.Company;
import org.nadojob.nadojobbackend.entity.CompanyUser;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.mapper.CompanyUserMapper;
import org.nadojob.nadojobbackend.repository.CompanyUserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyUserService {

    private final CompanyUserRepository companyUserRepository;
    private final CompanyUserMapper companyUserMapper;

    public CompanyUser create(Company company, User user) {
        CompanyUser companyUser = companyUserMapper.toEntity(company, user);
        return companyUserRepository.save(companyUser);
    }

}

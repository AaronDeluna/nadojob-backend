package org.nadojob.nadojobbackend.service.company;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.dto.PageDto;
import org.nadojob.nadojobbackend.dto.company.CompanyCreationDto;
import org.nadojob.nadojobbackend.dto.company.CompanyResponseDto;
import org.nadojob.nadojobbackend.dto.company.CompanyUpdateDto;
import org.nadojob.nadojobbackend.entity.Company;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.exception.CompanyNotFoundException;
import org.nadojob.nadojobbackend.mapper.CompanyMapper;
import org.nadojob.nadojobbackend.repository.CompanyRepository;
import org.nadojob.nadojobbackend.validation.CompanyValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final CompanyValidator companyValidator;

    public Company create(CompanyCreationDto dto, User creator) {
        companyValidator.validateCompanyNameDuplicate(dto.getName());
        Company company = companyMapper.toEntity(dto);
        company.setCreatedBy(creator);
        return companyRepository.save(company);
    }

    public PageDto<CompanyResponseDto> findAll(int pageSize, int page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Company> companyPage = companyRepository.findAll(pageable);

        List<CompanyResponseDto> companies = companyMapper.toResponseListDto(companyPage.getContent());
        return new PageDto<>(companies, companyPage.getTotalPages(), page, pageSize, companyPage.getTotalElements());
    }

    public CompanyResponseDto findByCurrentUser(UUID userId) {
        return companyMapper.toResponseDto(findByCurrentUserId(userId));
    }

    public CompanyResponseDto findById(UUID id) {
        return companyMapper.toResponseDto(companyRepository.findById(id).orElseThrow(
                () -> new CompanyNotFoundException("Компания не найдена")
        ));
    }

    @Transactional
    public CompanyResponseDto updateByCurrentUser(CompanyUpdateDto dto, UUID userId) {
        companyValidator.validateCompanyNameDuplicate(dto.getName());
        Company company = findByCurrentUserId(userId);
        companyMapper.update(company, dto);
        return companyMapper.toResponseDto(company);
    }

    private Company findByCurrentUserId(UUID userId) {
        return companyRepository.findByCreatedById(userId).orElseThrow(
                () -> new CompanyNotFoundException("Компания не найдена")
        );
    }

}

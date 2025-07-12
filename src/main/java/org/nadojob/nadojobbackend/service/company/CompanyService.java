package org.nadojob.nadojobbackend.service.company;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nadojob.nadojobbackend.dto.PageDto;
import org.nadojob.nadojobbackend.dto.company.AcceptInviteRequestDto;
import org.nadojob.nadojobbackend.dto.company.CompanyCreationDto;
import org.nadojob.nadojobbackend.dto.company.CompanyResponseDto;
import org.nadojob.nadojobbackend.dto.company.CompanyUpdateDto;
import org.nadojob.nadojobbackend.entity.Company;
import org.nadojob.nadojobbackend.entity.CompanyInvite;
import org.nadojob.nadojobbackend.entity.Sector;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.exception.CompanyNotFoundException;
import org.nadojob.nadojobbackend.exception.UserNotFoundException;
import org.nadojob.nadojobbackend.mapper.CompanyMapper;
import org.nadojob.nadojobbackend.repository.CompanyRepository;
import org.nadojob.nadojobbackend.repository.SectorRepository;
import org.nadojob.nadojobbackend.repository.UserRepository;
import org.nadojob.nadojobbackend.service.UserService;
import org.nadojob.nadojobbackend.validation.CompanyInviteValidator;
import org.nadojob.nadojobbackend.validation.CompanyValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.nadojob.nadojobbackend.entity.CompanyInviteStatus.ACCEPTED;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final SectorRepository sectorRepository;
    private final UserRepository userRepository;
    private final CompanyMapper companyMapper;
    private final CompanyValidator companyValidator;
    private final CompanyInviteValidator companyInviteValidator;
    private final CompanyInviteService companyInviteService;
    private final UserService userService;

    public Company create(CompanyCreationDto dto, User creator) {
        companyValidator.validateCompanyNameDuplicate(dto.getName());
        Company company = companyMapper.toEntity(dto);
        company.setCreatedBy(creator);
        return companyRepository.save(company);
    }

    public String inviteUserByEmail(String email, UUID ownerUserId) {
        companyInviteValidator.validateEmailNotExists(email);
        Company company = findByCurrentUserId(ownerUserId);
        User user = userRepository.findById(ownerUserId).orElseThrow(
                () -> new UserNotFoundException("Пользотвель не найден")
        );
        return companyInviteService.create(company, user, email);
    }

    @Transactional
    public User acceptInvite(AcceptInviteRequestDto dto) {
        CompanyInvite companyInvite = companyInviteService.findByToken(dto.getToken());
        companyInviteValidator.validateInviteToken(companyInvite);
        User user = userService.createFromInvite(companyInvite, dto);
        companyInviteService.updateStatusByToken(dto.getToken(), ACCEPTED);
        return user;
    }

    public PageDto<CompanyResponseDto> findAll(int pageSize, int page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Company> companyPage = companyRepository.findAll(pageable);

        List<CompanyResponseDto> companies = companyMapper.toResponseListDto(companyPage.getContent());
        return new PageDto<>(companies, companyPage.getTotalPages(), page, pageSize, companyPage.getTotalElements());
    }

    public CompanyResponseDto findByOwnerId(UUID userId) {
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
        List<Sector> sectors = sectorRepository.findByNameIn(dto.getSectors());
        company.setSectors(new HashSet<>(sectors));
        companyMapper.update(company, dto);
        return companyMapper.toResponseDto(company);
    }

    public Company findByCurrentUserId(UUID userId) {
        return companyRepository.findByCreatedById(userId).orElseThrow(
                () -> new CompanyNotFoundException("Компания не найдена")
        );
    }

}

package org.nadojob.nadojobbackend.service.company;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.entity.Company;
import org.nadojob.nadojobbackend.entity.CompanyInvite;
import org.nadojob.nadojobbackend.entity.CompanyInviteStatus;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.exception.InviteTokenNotFoundException;
import org.nadojob.nadojobbackend.repository.CompanyInviteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.nadojob.nadojobbackend.entity.UserRole.EMPLOYER;

@Service
@RequiredArgsConstructor
public class CompanyInviteService {

    private final CompanyInviteRepository companyInviteRepository;
    private final PasswordEncoder passwordEncoder;

    public String create(Company company, User user, String inviteUserEmail) {
        CompanyInvite companyInvite = companyInviteRepository.findByEmail(inviteUserEmail)
                .orElseGet(() -> creteNew(company, user, inviteUserEmail));
        return companyInvite.getToken();
    }

    public CompanyInvite findByToken(String token) {
        return companyInviteRepository.findByToken(token).orElseThrow(
                () -> new InviteTokenNotFoundException("Токен приглашения не найден")
        );
    }

    public void updateStatusByToken(String token, CompanyInviteStatus newStatus) {
        CompanyInvite companyInvite = findByToken(token);
        companyInvite.setStatus(newStatus);
        companyInviteRepository.save(companyInvite);
    }

    private CompanyInvite creteNew(Company company, User user, String inviteUserEmail) {
        CompanyInvite companyInvite = new CompanyInvite();
        companyInvite.setCompany(company);
        companyInvite.setEmail(inviteUserEmail);
        companyInvite.setRoleInCompany(EMPLOYER);
        companyInvite.setToken(passwordEncoder.encode(inviteUserEmail));
        companyInvite.setInvitedBy(user);
        companyInvite.setStatus(CompanyInviteStatus.PENDING);
        companyInvite.setExpiresAt(LocalDateTime.now().plusHours(24));
        companyInviteRepository.save(companyInvite);
        return companyInvite;
    }

}

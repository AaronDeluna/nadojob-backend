package org.nadojob.nadojobbackend.service.company;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nadojob.nadojobbackend.entity.Company;
import org.nadojob.nadojobbackend.entity.CompanyInvite;
import org.nadojob.nadojobbackend.entity.CompanyInviteStatus;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.exception.InviteTokenNotFoundException;
import org.nadojob.nadojobbackend.repository.CompanyInviteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.nadojob.nadojobbackend.entity.UserRole.EMPLOYER;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyInviteService {

    private final CompanyInviteRepository companyInviteRepository;

    public String create(Company company, User user, String inviteUserEmail) {
        Optional<CompanyInvite> optionalInvite = companyInviteRepository.findByEmail(inviteUserEmail)
                .filter(invite -> invite.getExpiresAt().isAfter(LocalDateTime.now()));

        if (optionalInvite.isPresent()) {
            log.info("Повторное использование активного приглашения для {}", inviteUserEmail);
            return optionalInvite.get().getToken();
        }

        return createNew(company, user, inviteUserEmail).getToken();
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

    private CompanyInvite createNew(Company company, User user, String inviteUserEmail) {
        CompanyInvite companyInvite = new CompanyInvite();
        companyInvite.setCompany(company);
        companyInvite.setEmail(inviteUserEmail);
        companyInvite.setRoleInCompany(EMPLOYER);
        companyInvite.setToken(UUID.randomUUID().toString());
        companyInvite.setInvitedBy(user);
        companyInvite.setStatus(CompanyInviteStatus.PENDING);
        companyInvite.setExpiresAt(LocalDateTime.now().plusHours(24));
        companyInviteRepository.save(companyInvite);
        return companyInvite;
    }

}

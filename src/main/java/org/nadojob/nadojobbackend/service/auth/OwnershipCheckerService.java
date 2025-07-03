package org.nadojob.nadojobbackend.service.auth;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.repository.CandidateProfileRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("ownershipChecker")
@RequiredArgsConstructor
public class OwnershipCheckerService {

    private final CandidateProfileRepository candidateProfileRepository;

    public boolean isOwner(UUID profileId, UUID userId) {
        return candidateProfileRepository.findById(profileId)
                .map(profile -> profile.getUser().getId().equals(userId))
                .orElse(false);
    }
}

package org.nadojob.nadojobbackend.service;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.repository.CandidateProfileRepository;
import org.springframework.stereotype.Service;

@Service(value = "candidateProfileSecurityService")
@RequiredArgsConstructor
public class CandidateProfileSecurityService {

    private final CandidateProfileRepository candidateProfileRepository;

//    public boolean isOwnerUser(UUID candidateProfileId, UUID userId) {
//        CandidateProfile candidateProfile = can
//    }

}

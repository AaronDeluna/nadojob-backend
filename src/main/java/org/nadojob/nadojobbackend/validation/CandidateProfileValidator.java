package org.nadojob.nadojobbackend.validation;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.exception.ProfileTitleAlreadyExistsException;
import org.nadojob.nadojobbackend.repository.CandidateProfileRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CandidateProfileValidator {

    private final CandidateProfileRepository candidateProfileRepository;

    public void validateTitleUniqueness(String title, UUID userId) {
        if (candidateProfileRepository.existsByTitleAndUserId(title, userId)) {
            throw new ProfileTitleAlreadyExistsException("У вас уже есть резюме с таким названием");
        }
    }

}

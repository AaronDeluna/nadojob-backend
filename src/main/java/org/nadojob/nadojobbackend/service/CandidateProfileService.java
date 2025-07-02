package org.nadojob.nadojobbackend.service;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.dto.candidate_profile.CandidateProfileRequestDto;
import org.nadojob.nadojobbackend.dto.candidate_profile.CandidateProfileResponseDto;
import org.nadojob.nadojobbackend.dto.candidate_profile.CandidateProfileUpdateDto;
import org.nadojob.nadojobbackend.entity.CandidateProfile;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.exception.CandidateProfileNotFoundException;
import org.nadojob.nadojobbackend.exception.UserNotFoundException;
import org.nadojob.nadojobbackend.mapper.CandidateProfileMapper;
import org.nadojob.nadojobbackend.repository.CandidateProfileRepository;
import org.nadojob.nadojobbackend.repository.UserRepository;
import org.nadojob.nadojobbackend.validation.CandidateProfileValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;
    private final UserRepository userRepository;
    private final CandidateProfileMapper candidateProfileMapper;
    private final CandidateProfileValidator candidateProfileValidator;

    @Transactional
    public CandidateProfileResponseDto create(CandidateProfileRequestDto dto, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(String.format("Пользователь c id: '%s' не найден", userId))
        );
        candidateProfileValidator.validateTitleUniqueness(dto.getTitle(), userId);
        CandidateProfile candidateProfile = candidateProfileMapper.toEntity(dto);
        candidateProfile.setUser(user);
        candidateProfileRepository.save(candidateProfile);
        return candidateProfileMapper.toResponseDto(candidateProfile);
    }

    public List<CandidateProfileResponseDto> findAllByCurrentUser(UUID userId) {
        return candidateProfileMapper.toResponseListDto(candidateProfileRepository.findAllByUserId(userId));
    }

    public CandidateProfileResponseDto findById(UUID id) {
        CandidateProfile candidateProfile = candidateProfileRepository.findById(id).orElseThrow(
                () -> new CandidateProfileNotFoundException(String.format("Резюме с id: '%s' не найдено", id))
        );
        return candidateProfileMapper.toResponseDto(candidateProfile);
    }

    @Transactional
    public CandidateProfileResponseDto updateById(UUID id, CandidateProfileUpdateDto dto) {
        CandidateProfile candidateProfile = candidateProfileRepository.findById(id).orElseThrow(
                () -> new CandidateProfileNotFoundException(String.format("Резюме с id: '%s' не найдено", id))
        );
        candidateProfileValidator.validateTitleUniqueness(dto.getTitle(), candidateProfile.getUser().getId());
        candidateProfileMapper.update(candidateProfile, dto);
        return candidateProfileMapper.toResponseDto(candidateProfile);
    }

    @Transactional
    public void deleteById(UUID id) {
        if (!candidateProfileRepository.existsById(id)) {
            throw new CandidateProfileNotFoundException(String.format("Резюме с id: '%s' не найдено", id));
        }
        candidateProfileRepository.deleteById(id);
    }

}

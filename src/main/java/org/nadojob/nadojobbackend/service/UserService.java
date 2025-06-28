package org.nadojob.nadojobbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nadojob.nadojobbackend.dto.auth.candidate.CandidateRegistrationRequestDto;
import org.nadojob.nadojobbackend.dto.auth.employer.EmployerRegistrationRequestDto;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.exception.UserNotFoundException;
import org.nadojob.nadojobbackend.mapper.UserMapper;
import org.nadojob.nadojobbackend.repository.UserRepository;
import org.nadojob.nadojobbackend.validation.UserValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public User createCandidate(CandidateRegistrationRequestDto dto) {
        userValidator.validateEmailDuplicate(dto.getEmail());
        return userRepository.save(userMapper.toCandidateEntity(dto, passwordEncoder));
    }

    public User createEmployer(EmployerRegistrationRequestDto dto) {
        userValidator.validateEmailDuplicate(dto.getEmail());
        userValidator.validatePhoneDuplicate(dto.getPhone());
        return userRepository.save(userMapper.toEmployerEntity(dto, passwordEncoder));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Почта указана неверно или не существует")
        );
    }

}

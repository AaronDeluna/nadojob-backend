package org.nadojob.nadojobbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nadojob.nadojobbackend.dto.auth.candidate.CandidateRegistrationRequestDto;
import org.nadojob.nadojobbackend.dto.auth.employer.EmployerRegistrationRequestDto;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.exception.EmailAlreadyExistsException;
import org.nadojob.nadojobbackend.mapper.UserMapper;
import org.nadojob.nadojobbackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User createCandidate(CandidateRegistrationRequestDto dto) {
        validateEmailDuplicate(dto.getEmail());
        return userRepository.save(userMapper.toCandidateEntity(dto, passwordEncoder));
    }

    public User createEmployer(EmployerRegistrationRequestDto dto) {
        validateEmailDuplicate(dto.getEmail());
        return userRepository.save(userMapper.toEmployerEntity(dto, passwordEncoder));
    }

    private void validateEmailDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Пользователь с такой почтой уже зарегистрирован");
        }
    }

}

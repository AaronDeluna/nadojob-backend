package org.nadojob.nadojobbackend.service.auth;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.dto.auth.AuthenticationResponseDto;
import org.nadojob.nadojobbackend.dto.auth.candidate.CandidateLoginRequestDto;
import org.nadojob.nadojobbackend.dto.auth.candidate.CandidateRegistrationRequestDto;
import org.nadojob.nadojobbackend.dto.auth.employer.EmployerRegistrationRequestDto;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.exception.PasswordNotCorrectException;
import org.nadojob.nadojobbackend.exception.UserNotFoundException;
import org.nadojob.nadojobbackend.repository.UserRepository;
import org.nadojob.nadojobbackend.service.CompanyService;
import org.nadojob.nadojobbackend.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final CompanyService companyService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthenticationResponseDto registerCandidate(CandidateRegistrationRequestDto dto) {
        User user = userService.createCandidate(dto);
        return generateTokenResponse(user);
    }

    public AuthenticationResponseDto loginCandidate(CandidateLoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new UserNotFoundException("Почта указана не врено или не сущестует")
        );
        isCorrectPassword(dto.getPassword(), user.getHashedPassword());
        return generateTokenResponse(user);
    }

    public AuthenticationResponseDto registerEmployer(EmployerRegistrationRequestDto dto) {
        User employer = userService.createEmployer(dto);
        companyService.create(dto, employer);
        return generateTokenResponse(employer);
    }

    private AuthenticationResponseDto generateTokenResponse(User user) {
        return new AuthenticationResponseDto(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user)
        );
    }

    private void isCorrectPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new PasswordNotCorrectException("Пароль не верный");
        }
    }

}

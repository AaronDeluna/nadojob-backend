package org.nadojob.nadojobbackend.service.auth;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.dto.auth.AuthenticationResponseDto;
import org.nadojob.nadojobbackend.dto.auth.LoginRequestDto;
import org.nadojob.nadojobbackend.dto.auth.candidate.CandidateRegistrationRequestDto;
import org.nadojob.nadojobbackend.dto.auth.employer.EmployerRegistrationRequestDto;
import org.nadojob.nadojobbackend.entity.Company;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.exception.PasswordNotCorrectException;
import org.nadojob.nadojobbackend.exception.UserNotFoundException;
import org.nadojob.nadojobbackend.repository.UserRepository;
import org.nadojob.nadojobbackend.service.company.CompanyService;
import org.nadojob.nadojobbackend.service.company.CompanyUserService;
import org.nadojob.nadojobbackend.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final CompanyService companyService;
    private final CompanyUserService companyUserService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthenticationResponseDto login(LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new UserNotFoundException("Почта указана не врено или не сущестует")
        );
        isCorrectPassword(dto.getPassword(), user.getHashedPassword());
        return generateTokenResponse(user);
    }

    public AuthenticationResponseDto registerCandidate(CandidateRegistrationRequestDto dto) {
        User user = userService.createCandidate(dto);
        return generateTokenResponse(user);
    }

    @Transactional
    public AuthenticationResponseDto registerEmployer(EmployerRegistrationRequestDto dto) {
        User employer = userService.createEmployer(dto);
        Company company = companyService.create(dto);
        companyUserService.create(company, employer);
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

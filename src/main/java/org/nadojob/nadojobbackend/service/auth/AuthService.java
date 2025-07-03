package org.nadojob.nadojobbackend.service.auth;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.dto.auth.AuthenticationResponseDto;
import org.nadojob.nadojobbackend.dto.auth.CandidateRegistrationRequestDto;
import org.nadojob.nadojobbackend.dto.auth.EmployerRegistrationRequestDto;
import org.nadojob.nadojobbackend.dto.auth.LoginRequestDto;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.service.UserService;
import org.nadojob.nadojobbackend.service.company.CompanyRegistrationService;
import org.nadojob.nadojobbackend.validation.UserValidator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final CompanyRegistrationService companyRegistrationService;
    private final JwtService jwtService;
    private final UserValidator userValidator;

    public AuthenticationResponseDto login(LoginRequestDto dto) {
        User user = userService.findByEmail(dto.getEmail());
        userValidator.checkIfBlocked(user);
        userValidator.validatePassword(dto.getPassword(), user.getHashedPassword());
        return generateTokenResponse(user);
    }

    public AuthenticationResponseDto registerCandidate(CandidateRegistrationRequestDto dto) {
        User user = userService.createCandidate(dto);
        return generateTokenResponse(user);
    }

    public AuthenticationResponseDto registerEmployer(EmployerRegistrationRequestDto dto) {
        User user = companyRegistrationService.registerCompanyWithOwner(dto);
        return generateTokenResponse(user);
    }

    private AuthenticationResponseDto generateTokenResponse(User user) {
        return new AuthenticationResponseDto(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user)
        );
    }

}

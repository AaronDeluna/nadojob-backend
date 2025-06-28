package org.nadojob.nadojobbackend.controller.auth;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.dto.auth.AuthenticationResponseDto;
import org.nadojob.nadojobbackend.dto.auth.LoginRequestDto;
import org.nadojob.nadojobbackend.dto.auth.candidate.CandidateRegistrationRequestDto;
import org.nadojob.nadojobbackend.dto.auth.employer.EmployerRegistrationRequestDto;
import org.nadojob.nadojobbackend.service.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/candidate")
    public ResponseEntity<AuthenticationResponseDto> registrationCandidate(
            @RequestBody CandidateRegistrationRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerCandidate(dto));
    }

    @PostMapping("/employer")
    public ResponseEntity<AuthenticationResponseDto> registrationEmployer(
            @RequestBody EmployerRegistrationRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerEmployer(dto));
    }

}

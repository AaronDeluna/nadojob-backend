package org.nadojob.nadojobbackend.controller;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.dto.auth.AuthenticationResponseDto;
import org.nadojob.nadojobbackend.dto.auth.candidate.CandidateRegistrationRequestDto;
import org.nadojob.nadojobbackend.dto.auth.employer.EmployerRegistrationRequestDto;
import org.nadojob.nadojobbackend.service.UserService;
import org.nadojob.nadojobbackend.service.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/employer")
@RequiredArgsConstructor
public class EmployerAuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> registration(@RequestBody EmployerRegistrationRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerEmployer(dto));
    }

}

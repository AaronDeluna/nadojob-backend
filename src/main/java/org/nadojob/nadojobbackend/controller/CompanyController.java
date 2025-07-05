package org.nadojob.nadojobbackend.controller;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.dto.PageDto;
import org.nadojob.nadojobbackend.dto.company.CompanyResponseDto;
import org.nadojob.nadojobbackend.dto.company.CompanyUpdateDto;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.service.company.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/invite")
    @PreAuthorize("hasAnyRole('ADMIN','COMPANY_OWNER')")
    public ResponseEntity<String> inviteUserByEmail(@RequestParam String email,
                                                    @AuthenticationPrincipal User principal) {
        return ResponseEntity.status(CREATED).body(companyService.inviteUserByEmail(email, principal.getId()));
    }

    @GetMapping
    public ResponseEntity<PageDto<CompanyResponseDto>> getAll(@RequestParam(defaultValue = "10") Integer pageSize,
                                                              @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(companyService.findAll(pageSize, page));
    }

    @GetMapping("/me")
    public ResponseEntity<CompanyResponseDto> getByCurrentUser(@AuthenticationPrincipal User principal) {
        return ResponseEntity.ok(companyService.findByCurrentUser(principal.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(companyService.findById(id));
    }

    @PutMapping("/me")
    public ResponseEntity<CompanyResponseDto> updateByCurrentUser(@RequestBody CompanyUpdateDto dto,
                                                                  @AuthenticationPrincipal User principal) {
        return ResponseEntity.ok(companyService.updateByCurrentUser(dto, principal.getId()));
    }

}

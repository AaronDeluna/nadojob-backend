package org.nadojob.nadojobbackend.controller;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.dto.candidate_profile.CandidateProfileRequestDto;
import org.nadojob.nadojobbackend.dto.candidate_profile.CandidateProfileResponseDto;
import org.nadojob.nadojobbackend.dto.candidate_profile.CandidateProfileUpdateDto;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.service.CandidateProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('CANDIDATE', 'ADMIN')")
public class CandidateProfileController {

    private final CandidateProfileService candidateProfileService;

    @PostMapping
    public ResponseEntity<CandidateProfileResponseDto> create(@RequestBody CandidateProfileRequestDto dto,
                                                              @AuthenticationPrincipal User principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(candidateProfileService.create(dto, principal.getId()));
    }

    @GetMapping
    public ResponseEntity<List<CandidateProfileResponseDto>> getByCurrentUser(@AuthenticationPrincipal User principal) {
        return ResponseEntity.ok(candidateProfileService.findAllByCurrentUser(principal.getId()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CANDIDATE', 'ADMIN', 'EMPLOYER')")
    public ResponseEntity<CandidateProfileResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(candidateProfileService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("@ownershipChecker.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<CandidateProfileResponseDto> updateById(@PathVariable UUID id,
                                                                  @RequestBody CandidateProfileUpdateDto dto) {
        return ResponseEntity.ok(candidateProfileService.updateById(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ownershipChecker.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable UUID id) {
        candidateProfileService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}

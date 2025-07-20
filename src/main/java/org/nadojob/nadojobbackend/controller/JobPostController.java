package org.nadojob.nadojobbackend.controller;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.dto.PageDto;
import org.nadojob.nadojobbackend.dto.job_post.JobApplicationRequestDto;
import org.nadojob.nadojobbackend.dto.job_post.JobPostRequestDto;
import org.nadojob.nadojobbackend.dto.job_post.JobPostResponseDto;
import org.nadojob.nadojobbackend.dto.job_post.JobPostUpdateDto;
import org.nadojob.nadojobbackend.entity.User;
import org.nadojob.nadojobbackend.service.job_post.JobPostService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobPostController {

    private final JobPostService jobPostService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER','COMPANY_OWNER')")
    public ResponseEntity<JobPostResponseDto> create(@RequestBody JobPostRequestDto dto,
                                                     @AuthenticationPrincipal User principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobPostService.create(dto, principal.getId()));
    }

    @PostMapping("/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<BigDecimal> apply(@RequestBody JobApplicationRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobPostService.applyToJob(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobPostResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(jobPostService.findById(id));
    }

    @GetMapping
    public ResponseEntity<PageDto<JobPostResponseDto>> getAll(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "50") int pageSize) {
        return ResponseEntity.ok(jobPostService.findAll(page, pageSize));
    }

    @GetMapping("/jobs")
    @PreAuthorize("hasAnyRole('ADMIN','COMPANY_OWNER')")
    public ResponseEntity<List<JobPostResponseDto>> getAllByCurrenUserCompany(@AuthenticationPrincipal User principal) {
        return ResponseEntity.ok(jobPostService.findAllByCurrentUserCompany(principal.getId()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER','COMPANY_OWNER') "
            + "and @jobPostSecurityService.isUserEmployerOfJobPost(#id, authentication.principal.id)")
    public ResponseEntity<JobPostResponseDto> updateById(@PathVariable UUID id,
                                                         @RequestBody JobPostUpdateDto dto) {
        return ResponseEntity.ok(jobPostService.updateById(id, dto));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYER','COMPANY_OWNER') "
            + "and @jobPostSecurityService.isUserEmployerOfJobPost(#id, authentication.principal.id)")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable UUID id) {
        jobPostService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}

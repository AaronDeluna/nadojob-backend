package org.nadojob.nadojobbackend.service.job_post;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.dto.PageDto;
import org.nadojob.nadojobbackend.dto.job_post.JobApplicationRequestDto;
import org.nadojob.nadojobbackend.dto.job_post.JobPostRequestDto;
import org.nadojob.nadojobbackend.dto.job_post.JobPostResponseDto;
import org.nadojob.nadojobbackend.dto.job_post.JobPostUpdateDto;
import org.nadojob.nadojobbackend.entity.CandidateProfile;
import org.nadojob.nadojobbackend.entity.Company;
import org.nadojob.nadojobbackend.entity.JobPost;
import org.nadojob.nadojobbackend.exception.CandidateProfileNotFoundException;
import org.nadojob.nadojobbackend.exception.JobPostNotFoundException;
import org.nadojob.nadojobbackend.mapper.CandidateProfileMapper;
import org.nadojob.nadojobbackend.mapper.JobPostMapper;
import org.nadojob.nadojobbackend.repository.CandidateProfileRepository;
import org.nadojob.nadojobbackend.repository.JobPostRepository;
import org.nadojob.nadojobbackend.service.company.CompanyService;
import org.nadojob.nadojobbackend.validation.JobPostValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.nadojob.nadojobbackend.entity.JobPostStatus.OPEN;

@Service
@RequiredArgsConstructor
public class JobPostService {

    private final static String SORT_BY_CREATED_AT = "createdAt";
    private static final String CANDIDATE_PROFILE_NOT_FOUND = "Резюме не найдено";
    public static final String JOB_POST_NOT_FOUND = "Вакансия не найдена";
    private final JobPostRepository jobPostRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final JobPostMapper jobPostMapper;
    private final CandidateProfileMapper candidateProfileMapper;
    private final CompanyService companyService;
    private final JobApplicationScoringService jobApplicationScoringService;
    private final JobApplicationService jobApplicationService;
    private final JobPostValidator jobPostValidator;

    public JobPostResponseDto create(JobPostRequestDto dto, UUID currentUserId) {
        Company company = companyService.findByCurrentUserId(currentUserId);
        JobPost jobPost = jobPostMapper.toEntity(dto);
        jobPost.setCompany(company);
        return jobPostMapper.toResponseDto(jobPostRepository.save(jobPost));
    }

    @Transactional
    public BigDecimal applyToJob(JobApplicationRequestDto dto) {
        JobPost jobPost = getJobPostById(dto.getJobPostId());
        CandidateProfile candidate = getCandidateById(dto.getCandidateProfileId());
        jobPostValidator.validateDuplicateApply(candidate.getId(), jobPost.getId());
        jobPostValidator.validateMaxApplicationsPerDay(candidate.getId());
        BigDecimal score = calculateCandidateScore(candidate, jobPost);
        jobApplicationService.create(candidate, jobPost, score);
        return score;
    }

    public JobPostResponseDto findById(UUID id) {
        return jobPostMapper.toResponseDto(getJobPostById(id));
    }

    public List<JobPostResponseDto> findAllByCurrentUserCompany(UUID userId) {
        UUID companyId = companyService.findByCurrentUserId(userId).getId();
        return jobPostMapper.toResponseDtoList(jobPostRepository.findAllByCompanyId(companyId));
    }

    public PageDto<JobPostResponseDto> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(SORT_BY_CREATED_AT).descending());
        Page<JobPost> jobPostPage = jobPostRepository.findByStatus(pageable, OPEN);
        List<JobPostResponseDto> jobPosts = jobPostMapper.toResponseDtoList(jobPostPage.getContent());
        return new PageDto<>(jobPosts, jobPostPage.getTotalPages(), page, pageSize, jobPostPage.getTotalElements());
    }

    @Transactional
    public JobPostResponseDto updateById(UUID id, JobPostUpdateDto dto) {
        JobPost jobPost = getJobPostById(id);
        jobPostMapper.update(jobPost, dto);
        return jobPostMapper.toResponseDto(jobPost);
    }

    public void deleteById(UUID id) {
        if (!jobPostRepository.existsById(id)) {
            throw new JobPostNotFoundException(JOB_POST_NOT_FOUND);
        }
        jobPostRepository.deleteById(id);
    }

    private CandidateProfile getCandidateById(UUID id) {
        return candidateProfileRepository.findById(id)
                .orElseThrow(() -> new CandidateProfileNotFoundException(CANDIDATE_PROFILE_NOT_FOUND));
    }

    private JobPost getJobPostById(UUID id) {
        return jobPostRepository.findById(id)
                .orElseThrow(() -> new JobPostNotFoundException(JOB_POST_NOT_FOUND));
    }

    private BigDecimal calculateCandidateScore(CandidateProfile candidate, JobPost jobPost) {
        return jobApplicationScoringService.scoreCandidate(
                candidateProfileMapper.toMatchingDto(candidate),
                jobPostMapper.toMatchingDto(jobPost)
        );
    }

}

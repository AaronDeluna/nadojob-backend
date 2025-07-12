package org.nadojob.nadojobbackend.service.job_post;

import lombok.RequiredArgsConstructor;
import org.nadojob.nadojobbackend.dto.PageDto;
import org.nadojob.nadojobbackend.dto.job_post.JobPostRequestDto;
import org.nadojob.nadojobbackend.dto.job_post.JobPostResponseDto;
import org.nadojob.nadojobbackend.dto.job_post.JobPostUpdateDto;
import org.nadojob.nadojobbackend.entity.Company;
import org.nadojob.nadojobbackend.entity.JobPost;
import org.nadojob.nadojobbackend.exception.JobPostNotFoundException;
import org.nadojob.nadojobbackend.mapper.JobPostMapper;
import org.nadojob.nadojobbackend.repository.JobPostRepository;
import org.nadojob.nadojobbackend.service.company.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.nadojob.nadojobbackend.entity.JobPostStatus.OPEN;

@Service
@RequiredArgsConstructor
public class JobPostService {

    private final static String SORT_BY_CREATE_AT = "createdAt";
    private final JobPostRepository jobPostRepository;
    private final JobPostMapper jobPostMapper;
    private final CompanyService companyService;

    public JobPostResponseDto create(JobPostRequestDto dto, UUID currentUserId) {
        Company company = companyService.findByCurrentUserId(currentUserId);
        JobPost jobPost = jobPostMapper.toEntity(dto);
        jobPost.setCompany(company);
        return jobPostMapper.toResponseDto(jobPostRepository.save(jobPost));
    }

    public JobPostResponseDto findById(UUID id) {
        return jobPostMapper.toResponseDto(getById(id));
    }

    public PageDto<JobPostResponseDto> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(SORT_BY_CREATE_AT).descending());
        Page<JobPost> jobPostPage = jobPostRepository.findByStatus(pageable, OPEN);
        List<JobPostResponseDto> jobPosts = jobPostMapper.toResponseDtoList(jobPostPage.getContent());
        return new PageDto<>(jobPosts, jobPostPage.getTotalPages(), page, pageSize, jobPostPage.getTotalElements());
    }

    public JobPostResponseDto updateById(UUID id, JobPostUpdateDto dto) {
        JobPost jobPost = getById(id);
        jobPostMapper.update(jobPost, dto);
        return jobPostMapper.toResponseDto(jobPost);
    }

    public void deleteById(UUID id) {
        if (!jobPostRepository.existsById(id)) {
            throw new JobPostNotFoundException("Вакансия не найдена");
        }
        jobPostRepository.deleteById(id);
    }

    private JobPost getById(UUID id) {
        return jobPostRepository.findById(id).orElseThrow(
                () -> new JobPostNotFoundException("Вакансия не найдена")
        );
    }

}

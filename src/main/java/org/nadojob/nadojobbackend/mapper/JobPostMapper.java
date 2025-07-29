package org.nadojob.nadojobbackend.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.nadojob.nadojobbackend.dto.job_post.JobPostMatchingDto;
import org.nadojob.nadojobbackend.dto.job_post.JobPostRequestDto;
import org.nadojob.nadojobbackend.dto.job_post.JobPostResponseDto;
import org.nadojob.nadojobbackend.dto.job_post.JobPostUpdateDto;
import org.nadojob.nadojobbackend.entity.JobPost;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface JobPostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deadlineAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "company", ignore = true)
    JobPost toEntity(JobPostRequestDto dto);

    JobPostResponseDto toResponseDto(JobPost entity);

    JobPostMatchingDto toMatchingDto(JobPost entity);

    List<JobPostResponseDto> toResponseDtoList(List<JobPost> entityList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget JobPost jobPost, JobPostUpdateDto dto);

}

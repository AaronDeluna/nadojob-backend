package org.nadojob.nadojobbackend.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.nadojob.nadojobbackend.dto.candidate_profile.CandidateMatchingDto;
import org.nadojob.nadojobbackend.dto.candidate_profile.CandidateProfileRequestDto;
import org.nadojob.nadojobbackend.dto.candidate_profile.CandidateProfileResponseDto;
import org.nadojob.nadojobbackend.dto.candidate_profile.CandidateProfileUpdateDto;
import org.nadojob.nadojobbackend.entity.CandidateProfile;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CandidateProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "skills", source = "skills")
    CandidateProfile toEntity(CandidateProfileRequestDto dto);

    @Mapping(target = "skills", source = "skills")
    CandidateProfileResponseDto toResponseDto(CandidateProfile entity);

    CandidateMatchingDto toMatchingDto(CandidateProfile entity);

    @Mapping(target = "skills", source = "skills")
    List<CandidateProfileResponseDto> toResponseListDto(List<CandidateProfile> entity);

    @Mapping(target = "skills", source = "skills")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget CandidateProfile candidateProfile, CandidateProfileUpdateDto candidateProfileUpdateDto);

}

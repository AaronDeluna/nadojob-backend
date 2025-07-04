package org.nadojob.nadojobbackend.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.nadojob.nadojobbackend.dto.company.CompanyCreationDto;
import org.nadojob.nadojobbackend.dto.auth.EmployerRegistrationRequestDto;
import org.nadojob.nadojobbackend.dto.company.CompanyResponseDto;
import org.nadojob.nadojobbackend.dto.company.CompanyUpdateDto;
import org.nadojob.nadojobbackend.entity.Company;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "logoUrl", ignore = true)
    @Mapping(target = "coverUrl", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "isVerified", ignore = true)
    @Mapping(target = "isBlocked", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "region", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "street", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Company toEntity(CompanyCreationDto dto);

    CompanyCreationDto toCreationDto(EmployerRegistrationRequestDto dto);

    CompanyResponseDto toResponseDto(Company entity);

    List<CompanyResponseDto> toResponseListDto(List<Company> companies);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Company company, CompanyUpdateDto dto);

}

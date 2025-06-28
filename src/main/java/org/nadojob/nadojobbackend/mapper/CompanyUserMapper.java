package org.nadojob.nadojobbackend.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.nadojob.nadojobbackend.entity.Company;
import org.nadojob.nadojobbackend.entity.CompanyUser;
import org.nadojob.nadojobbackend.entity.CompanyUserId;
import org.nadojob.nadojobbackend.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanyUserMapper {

    @Mapping(target = "id", ignore = true) // EmbeddedId зададим вручную
    @Mapping(target = "company", source = "company")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdAt", ignore = true)
    CompanyUser toEntity(Company company, User user);

    @AfterMapping
    default void setEmbeddedId(@MappingTarget CompanyUser companyUser) {
        if (companyUser.getUser() != null && companyUser.getCompany() != null) {
            CompanyUserId id = new CompanyUserId(
                    companyUser.getUser().getId(),
                    companyUser.getCompany().getId()
            );
            companyUser.setId(id);
        }
    }

}

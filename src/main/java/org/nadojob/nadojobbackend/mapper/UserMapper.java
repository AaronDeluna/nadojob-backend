package org.nadojob.nadojobbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.nadojob.nadojobbackend.dto.company.AcceptInviteRequestDto;
import org.nadojob.nadojobbackend.dto.user.UserCreationDto;
import org.nadojob.nadojobbackend.dto.user.UserDto;
import org.nadojob.nadojobbackend.dto.auth.CandidateRegistrationRequestDto;
import org.nadojob.nadojobbackend.dto.auth.EmployerRegistrationRequestDto;
import org.nadojob.nadojobbackend.entity.CompanyInvite;
import org.nadojob.nadojobbackend.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.nadojob.nadojobbackend.entity.UserRole.CANDIDATE;
import static org.nadojob.nadojobbackend.entity.UserRole.COMPANY_OWNER;
import static org.nadojob.nadojobbackend.entity.UserRole.EMPLOYER;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {

    public abstract UserDto toDto(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", source = "userRole")
    @Mapping(target = "hashedPassword", ignore = true)
    @Mapping(target = "profiles", ignore = true)
    @Mapping(target = "isBlocked", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract User toEntity(UserCreationDto dto);

    public User toCandidateEntity(CandidateRegistrationRequestDto dto, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setEmail(dto.getEmail().trim().toLowerCase());
        user.setHashedPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(CANDIDATE);
        user.setIsBlocked(false);
        return user;
    }

    public User toEmployerEntity(EmployerRegistrationRequestDto dto, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setEmail(dto.getEmail().trim().toLowerCase());
        user.setHashedPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setRole(COMPANY_OWNER);
        user.setIsBlocked(false);
        return user;
    }

    public User toInviteEntity(CompanyInvite companyInvite,
                               AcceptInviteRequestDto acceptInvite,
                               PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setRole(companyInvite.getRoleInCompany());
        user.setPhone(acceptInvite.getPhone());
        user.setEmail(acceptInvite.getPhone());
        user.setHashedPassword(passwordEncoder.encode(acceptInvite.getPassword()));
        return user;
    }

}

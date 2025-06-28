package org.nadojob.nadojobbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.nadojob.nadojobbackend.dto.UserDto;
import org.nadojob.nadojobbackend.dto.auth.candidate.CandidateRegistrationRequestDto;
import org.nadojob.nadojobbackend.dto.auth.employer.EmployerRegistrationRequestDto;
import org.nadojob.nadojobbackend.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.nadojob.nadojobbackend.entity.UserRole.CANDIDATE;
import static org.nadojob.nadojobbackend.entity.UserRole.EMPLOYER;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {

    public abstract UserDto toDto(User entity);

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
        user.setRole(EMPLOYER);
        user.setIsBlocked(false);
        return user;
    }

}

package org.nadojob.nadojobbackend.dto.candidate_profile;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class CandidateProfileResponseDto {
    private UUID id;
    private String title;
    private String fullName;
    private LocalDate dateOfBirth;
    private Integer desiredSalary;
    private String desiredCurrency;
    private String locationCity;
    private String aboutMe;
    private String avatarUrl;
    private List<String> skills;
    private List<LanguageDto> languages;
    private List<WorkExperienceDto> workExperience;
    private List<EducationDto> education;
    private ContactsExtraDto contactsExtra;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

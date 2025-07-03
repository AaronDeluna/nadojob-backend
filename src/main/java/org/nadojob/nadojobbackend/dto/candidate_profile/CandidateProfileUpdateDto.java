package org.nadojob.nadojobbackend.dto.candidate_profile;

import lombok.Data;
import org.nadojob.nadojobbackend.entity.Currency;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class CandidateProfileUpdateDto {
    private String title;
    private String fullName;
    private LocalDate dateOfBirth;
    private Integer desiredSalary;
    private Currency desiredCurrency;
    private String locationCity;
    private String aboutMe;
    private String avatarUrl;
    private List<String> skills;
    private List<LanguageDto> languages;
    private List<WorkExperienceDto> workExperience;
    private List<EducationDto> education;
    private Map<String, String> contactsExtra;

}

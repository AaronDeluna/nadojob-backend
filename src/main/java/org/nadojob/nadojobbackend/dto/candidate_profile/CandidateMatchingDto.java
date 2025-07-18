package org.nadojob.nadojobbackend.dto.candidate_profile;

import lombok.Data;

import java.util.List;

@Data
public class CandidateMatchingDto {
    private String title;
    private String fullName;
    private Integer desiredSalary;
    private String desiredCurrency;
    private String locationCity;
    private String aboutMe;
    private List<String> skills;
    private List<LanguageDto> languages;
    private List<WorkExperienceDto> workExperience;
    private List<EducationDto> education;

}

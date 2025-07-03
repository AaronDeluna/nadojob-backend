package org.nadojob.nadojobbackend.dto.candidate_profile;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkExperienceDto {
    private String company;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
}

package org.nadojob.nadojobbackend.dto.candidate_profile;

import lombok.Data;

@Data
public class EducationDto {
    private String institution;
    private String degree;
    private Integer graduationYear;
}

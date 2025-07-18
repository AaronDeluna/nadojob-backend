package org.nadojob.nadojobbackend.dto.job_post;

import lombok.Data;

import java.util.UUID;

@Data
public class JobApplicationRequestDto {
    private UUID jobPostId;
    private UUID candidateProfileId;

}

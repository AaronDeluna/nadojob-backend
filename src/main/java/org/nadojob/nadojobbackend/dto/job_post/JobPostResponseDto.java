package org.nadojob.nadojobbackend.dto.job_post;

import lombok.Data;
import org.nadojob.nadojobbackend.entity.Currency;

import java.util.UUID;

@Data
public class JobPostResponseDto {
    private UUID id;
    private String title;
    private String description;
    private String locationCity;
    private Integer compensationTo;
    private Integer compensationFrom;
    private Currency currency;

}

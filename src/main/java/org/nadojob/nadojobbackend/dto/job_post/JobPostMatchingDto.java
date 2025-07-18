package org.nadojob.nadojobbackend.dto.job_post;

import lombok.Data;
import org.nadojob.nadojobbackend.entity.Currency;

@Data
public class JobPostMatchingDto {
    private String title;
    private String description;
    private String locationCity;
    private Integer compensationTo;
    private Integer compensationFrom;
    private Currency currency;

}

package org.nadojob.nadojobbackend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyCreationDto {
    private String email;
    private String password;
    private String name;
    private String phone;

}

package org.nadojob.nadojobbackend.dto.company;

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

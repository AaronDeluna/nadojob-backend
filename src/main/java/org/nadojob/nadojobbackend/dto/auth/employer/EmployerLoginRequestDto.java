package org.nadojob.nadojobbackend.dto.auth.employer;

import lombok.Data;

@Data
public class EmployerLoginRequestDto {

    private String email;

    private String password;

}

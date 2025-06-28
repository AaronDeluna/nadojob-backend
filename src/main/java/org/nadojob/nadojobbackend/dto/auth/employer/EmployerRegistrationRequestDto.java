package org.nadojob.nadojobbackend.dto.auth.employer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployerRegistrationRequestDto {

    private static final int MIN_PASSWORD_SIZE = 6;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = MIN_PASSWORD_SIZE)
    private String password;

    @NotBlank
    private String name;

    private String phone;

}

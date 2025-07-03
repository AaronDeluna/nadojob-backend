package org.nadojob.nadojobbackend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CandidateRegistrationRequestDto {

    private static final int MIN_PASSWORD_SIZE = 6;

    @Email(message = "Неверный формат email")
    @NotEmpty(message = "Почта не может быть пустой")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = MIN_PASSWORD_SIZE, message = "Пароль должен быть минимум {min} символов")
    private String password;
}

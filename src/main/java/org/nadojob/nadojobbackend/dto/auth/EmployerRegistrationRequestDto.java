package org.nadojob.nadojobbackend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployerRegistrationRequestDto {

    private static final int MIN_PASSWORD_SIZE = 6;

    @Email
    @NotEmpty(message = "Почта пользователя не может быть пустым")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = MIN_PASSWORD_SIZE, message = "Пароль должен содержать минимум {min} символов")
    private String password;

    @NotBlank(message = "Имя компании не может быть пустым")
    private String name;

    @NotBlank(message = "Телефон не может быть пустым")
    private String phone;

}

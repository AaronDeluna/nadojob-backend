package org.nadojob.nadojobbackend.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotEmpty(message = "Почта пользователя не может быть пустым")
    private String email;

    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;

}

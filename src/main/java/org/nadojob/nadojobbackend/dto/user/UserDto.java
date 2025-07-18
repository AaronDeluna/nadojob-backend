package org.nadojob.nadojobbackend.dto.user;

import lombok.Data;
import org.nadojob.nadojobbackend.entity.UserRole;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private UserRole role;
    private String email;
    private String hashedPassword;
    private String phone;
    private Boolean isBlocked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

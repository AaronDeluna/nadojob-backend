package org.nadojob.nadojobbackend.dto.user;

import lombok.Data;
import org.nadojob.nadojobbackend.entity.UserRole;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private String email;
    private Boolean isBlocked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

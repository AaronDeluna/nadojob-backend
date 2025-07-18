package org.nadojob.nadojobbackend.dto.user;

import lombok.Builder;
import lombok.Data;
import org.nadojob.nadojobbackend.entity.UserRole;

@Data
@Builder
public class UserCreationDto {
    private UserRole userRole;
    private String email;
    private String password;
    private String phone;

}

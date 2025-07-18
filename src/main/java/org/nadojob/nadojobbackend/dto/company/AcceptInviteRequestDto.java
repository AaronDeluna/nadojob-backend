package org.nadojob.nadojobbackend.dto.company;

import lombok.Data;

@Data
public class AcceptInviteRequestDto {
    private String name;
    private String phone;
    private String password;
    private String token;

}

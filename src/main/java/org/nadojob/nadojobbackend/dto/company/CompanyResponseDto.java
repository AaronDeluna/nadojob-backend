package org.nadojob.nadojobbackend.dto.company;

import lombok.Data;
import org.nadojob.nadojobbackend.dto.user.UserResponseDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CompanyResponseDto {
    private UUID id;
    private String name;
    private String description;
    private String phone;
    private String logoUrl;
    private String coverUrl;
    private Double rating;
    private Boolean isBlocked;
    private String country;
    private String region;
    private String city;
    private String street;
    private LocalDateTime createdAt;

}

package org.nadojob.nadojobbackend.dto.company;

import lombok.Data;

import java.util.List;

@Data
public class CompanyUpdateDto {
    private String name;
    private String description;
    private String phone;
    private String logoUrl;
    private String coverUrl;
    private String country;
    private String region;
    private String city;
    private String street;
    private List<String> sectors;

}

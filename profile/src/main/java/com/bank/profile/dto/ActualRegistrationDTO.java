package com.bank.profile.dto;

import lombok.Data;

@Data
public class ActualRegistrationDTO {
    private String country;
    private String region;
    private String city;
    private String district;
    private String locality;
    private String street;
    private String house_number;
    private String house_block;
    private String flat_number;
    private Long index;
}

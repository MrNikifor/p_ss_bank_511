package com.bank.profile.dto;

import com.bank.profile.entity.ActualRegistration;
import com.bank.profile.entity.Passport;
import lombok.Data;

@Data
public class ProfileDTO {
    private Long phoneNumber;
    private String email;
    private String nameOnCard;
    private Long inn;
    private Long snils;
    private Passport passportId;
    private ActualRegistration actualRegistrationId;
}

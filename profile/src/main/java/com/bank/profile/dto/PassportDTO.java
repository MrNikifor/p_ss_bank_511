package com.bank.profile.dto;

import com.bank.profile.entity.exact.Passport;
import com.bank.profile.entity.exact.Registration;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PassportDTO {
    private Integer series;
    private Integer number;
    private String lastName;
    private String firstName;
    private Passport.Gender gender;
    private LocalDate birthDate;
    private String birthPlace;
    private String issuedBy;
    private LocalDate dateOfIssue;
    private Integer divisionCode;
    private Registration registrationId;
}

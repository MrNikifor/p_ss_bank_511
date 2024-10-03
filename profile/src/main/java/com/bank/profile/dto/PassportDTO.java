package com.bank.profile.dto;

import com.bank.profile.entity.Passport;
import com.bank.profile.entity.Registration;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Size;
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

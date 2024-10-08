package com.bank.profile.dto;

import com.bank.profile.dto.abstracts.AbstractDTO;
import com.bank.profile.entity.Passport;
import com.bank.profile.entity.Registration;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class PassportDTO extends AbstractDTO {
    private Long id;
    private Integer series;
    private Integer number;
    private String lastName;
    private String firstName;
    private String middleName;
    private Passport.Gender gender;
    private LocalDate birthDate;
    private String birthPlace;
    private String issuedBy;
    private LocalDate dateOfIssue;
    private Integer divisionCode;
    private LocalDate expirationDate;
    private Registration registrationId;
}

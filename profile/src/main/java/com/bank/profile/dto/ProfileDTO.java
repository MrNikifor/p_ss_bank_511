package com.bank.profile.dto;

import com.bank.profile.dto.abstracts.AbstractDTO;
import com.bank.profile.entity.ActualRegistration;
import com.bank.profile.entity.Passport;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProfileDTO extends AbstractDTO {
    private Long id;
    private Long phoneNumber;
    private String email;
    private String nameOnCard;
    private Long inn;
    private Long snils;
    private Passport passportId;
    private ActualRegistration actualRegistrationId;
}

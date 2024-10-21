package com.bank.profile.dto;

import com.bank.profile.dto.abstracts.AbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

@Data
@Component
@EqualsAndHashCode(callSuper = true)
public class ActualRegistrationDTO extends AbstractDTO {
    private Long id;
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

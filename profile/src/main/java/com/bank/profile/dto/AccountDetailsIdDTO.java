package com.bank.profile.dto;

import com.bank.profile.dto.abstracts.AbstractDTO;
import com.bank.profile.entity.Profile;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccountDetailsIdDTO extends AbstractDTO {
    private Long id;
    private Long accountId;
    private Profile profileId;
}

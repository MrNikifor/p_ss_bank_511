package com.bank.profile.dto;

import com.bank.profile.entity.Profile;
import lombok.Data;

@Data
public class AccountDetailsIdDTO {
    private Long accountId;
    private Profile profileId;
}

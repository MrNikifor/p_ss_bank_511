package com.bank.profile.mappers;

import com.bank.profile.dto.AccountDetailsIdDTO;
import com.bank.profile.entity.AccountDetailsId;
import com.bank.profile.mappers.config.MapStructConfig;
import com.bank.profile.mappers.generics.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface AccountDetailsIdMapper extends BaseMapper<AccountDetailsId, AccountDetailsIdDTO> {
}

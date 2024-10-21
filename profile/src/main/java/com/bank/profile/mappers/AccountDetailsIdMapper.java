package com.bank.profile.mappers;

import com.bank.profile.dto.AccountDetailsIdDTO;
import com.bank.profile.entity.AccountDetailsId;
import com.bank.profile.mappers.config.MapStructConfig;
import com.bank.profile.mappers.generics.BaseMapper;
import com.bank.profile.service.generics.AbstractBaseCrudService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class, uses = {AbstractBaseCrudService.class})
public interface AccountDetailsIdMapper extends BaseMapper<AccountDetailsId, AccountDetailsIdDTO> {

    @Override
    @Mapping(source = "id", target = "id")
    AccountDetailsId toEntity(AccountDetailsIdDTO accountDetailsIdDTO);
}

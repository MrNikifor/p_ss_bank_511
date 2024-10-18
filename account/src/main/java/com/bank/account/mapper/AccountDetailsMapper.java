package com.bank.account.mapper;

import com.bank.account.dto.AccountDetailsDTO;
import com.bank.account.entity.AccountDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountDetailsMapper {
    AccountDetailsMapper INSTANCE = Mappers.getMapper(AccountDetailsMapper.class);
    AccountDetails toEntity(AccountDetailsDTO accountDetailsDTO);
    AccountDetailsDTO toDTO(AccountDetails accountDetails);
}
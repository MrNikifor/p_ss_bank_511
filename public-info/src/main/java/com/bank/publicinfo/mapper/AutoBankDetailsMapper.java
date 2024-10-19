package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.model.BankDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoBankDetailsMapper {

    AutoBankDetailsMapper BANK_DETAILS_MAPPER = Mappers.getMapper(AutoBankDetailsMapper.class);

    @Mapping(target = "id", ignore = true)
    BankDetails mapToBankDetails(BankDetailsDto bankDetailsDto);

    BankDetailsDto mapToBankDetailsDto(BankDetails bankDetails);

    void updateBankDetailsFromDto(BankDetailsDto bankDetailsDto, @MappingTarget BankDetails bankDetails);
}
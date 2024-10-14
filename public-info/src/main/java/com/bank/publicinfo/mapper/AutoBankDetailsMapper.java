package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BankDetailsDTO;
import com.bank.publicinfo.model.BankDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoBankDetailsMapper {

    AutoBankDetailsMapper BANK_DETAILS_MAPPER = Mappers.getMapper(AutoBankDetailsMapper.class);

    BankDetailsDTO mapToBankDetailsDTO(BankDetails bankDetails);

    BankDetails mapToBankDetails(BankDetailsDTO bankDetailsDTO);
}

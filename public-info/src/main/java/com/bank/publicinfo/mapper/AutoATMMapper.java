package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.ATMDTO;
import com.bank.publicinfo.model.ATM;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoATMMapper {

    AutoATMMapper ATM_MAPPER = Mappers.getMapper(AutoATMMapper.class);

    ATMDTO mapToATMDTO(ATM atm);

    ATM mapToATM(ATMDTO atmDTO);
}

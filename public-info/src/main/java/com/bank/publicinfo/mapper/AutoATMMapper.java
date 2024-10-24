package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.ATMDto;
import com.bank.publicinfo.model.ATM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoATMMapper {

    AutoATMMapper ATM_MAPPER = Mappers.getMapper(AutoATMMapper.class);

    @Mapping(target = "id", ignore = true)
    ATM mapToATM(ATMDto atmDto);

    ATMDto mapToATMDto(ATM atm);

    void updateATMFromDto(ATMDto atmDto, @MappingTarget ATM atm);
}
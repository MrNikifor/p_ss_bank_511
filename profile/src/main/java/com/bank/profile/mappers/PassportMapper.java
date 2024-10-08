package com.bank.profile.mappers;

import com.bank.profile.dto.PassportDTO;
import com.bank.profile.dto.RegistrationDTO;
import com.bank.profile.entity.Passport;
import com.bank.profile.entity.Registration;
import com.bank.profile.mappers.generics.BaseMapper;
import com.bank.profile.mappers.config.MapStructConfig;
import com.bank.profile.service.generics.AbstractBaseCrudService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class, uses = { AbstractBaseCrudService.class })
public interface PassportMapper extends BaseMapper<Passport, PassportDTO> {

    @Mapping(source = "id", target = "id")
    Passport toEntity(PassportDTO passportDTO);
}

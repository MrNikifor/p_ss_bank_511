package com.bank.profile.mappers;

import com.bank.profile.dto.RegistrationDTO;
import com.bank.profile.entity.Registration;
import com.bank.profile.mappers.config.MapStructConfig;
import com.bank.profile.mappers.generics.BaseMapper;
import com.bank.profile.service.generics.AbstractBaseCrudService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class, uses = {AbstractBaseCrudService.class})
public interface RegistrationMapper extends BaseMapper<Registration, RegistrationDTO> {

    @Override
    @Mapping(source = "id", target = "id")
    Registration toEntity(RegistrationDTO registrationDTO);
}

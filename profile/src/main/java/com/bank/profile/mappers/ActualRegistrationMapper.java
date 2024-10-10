package com.bank.profile.mappers;

import com.bank.profile.dto.ActualRegistrationDTO;
import com.bank.profile.entity.ActualRegistration;
import com.bank.profile.mappers.config.MapStructConfig;
import com.bank.profile.mappers.generics.BaseMapper;
import com.bank.profile.service.generics.AbstractBaseCrudService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class, uses = {AbstractBaseCrudService.class})
public interface ActualRegistrationMapper extends BaseMapper<ActualRegistration, ActualRegistrationDTO> {

    @Override
    @Mapping(source = "id", target = "id")
    ActualRegistration toEntity(ActualRegistrationDTO actualRegistrationDTO);
}

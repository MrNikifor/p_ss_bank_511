package com.bank.profile.mappers;

import com.bank.profile.dto.ActualRegistrationDTO;
import com.bank.profile.entity.ActualRegistration;
import com.bank.profile.mappers.config.MapStructConfig;
import com.bank.profile.mappers.generics.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface ActualRegistrationMapper extends BaseMapper<ActualRegistration, ActualRegistrationDTO> {
}

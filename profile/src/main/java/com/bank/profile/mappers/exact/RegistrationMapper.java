package com.bank.profile.mappers.exact;

import com.bank.profile.dto.RegistrationDTO;
import com.bank.profile.entity.Registration;
import com.bank.profile.mappers.BaseMapper;
import com.bank.profile.mappers.config.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface RegistrationMapper extends BaseMapper<Registration, RegistrationDTO> {
}

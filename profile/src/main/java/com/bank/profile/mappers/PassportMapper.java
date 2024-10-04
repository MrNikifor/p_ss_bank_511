package com.bank.profile.mappers;

import com.bank.profile.dto.PassportDTO;
import com.bank.profile.entity.exact.Passport;
import com.bank.profile.mappers.generics.BaseMapper;
import com.bank.profile.mappers.config.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface PassportMapper extends BaseMapper<Passport, PassportDTO> {
}

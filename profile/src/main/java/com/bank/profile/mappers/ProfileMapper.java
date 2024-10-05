package com.bank.profile.mappers;

import com.bank.profile.dto.ProfileDTO;
import com.bank.profile.entity.Profile;
import com.bank.profile.mappers.generics.BaseMapper;
import com.bank.profile.mappers.config.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface ProfileMapper extends BaseMapper<Profile, ProfileDTO> {
}

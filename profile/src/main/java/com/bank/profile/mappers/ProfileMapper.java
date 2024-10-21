package com.bank.profile.mappers;

import com.bank.profile.dto.ProfileDTO;
import com.bank.profile.entity.Profile;
import com.bank.profile.mappers.config.MapStructConfig;
import com.bank.profile.mappers.generics.BaseMapper;
import com.bank.profile.service.generics.AbstractBaseCrudService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class, uses = {AbstractBaseCrudService.class})
public interface ProfileMapper extends BaseMapper<Profile, ProfileDTO> {

    @Override
    @Mapping(source = "id", target = "id")
    Profile toEntity(ProfileDTO profileDTO);
}

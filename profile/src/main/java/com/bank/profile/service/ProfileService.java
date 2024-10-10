package com.bank.profile.service;

import com.bank.profile.dto.ProfileDTO;
import com.bank.profile.entity.Profile;
import com.bank.profile.mappers.ProfileMapper;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.generics.AbstractBaseCrudService;
import org.springframework.stereotype.Service;

@Service
public class ProfileService extends AbstractBaseCrudService<Profile, ProfileDTO, ProfileRepository, ProfileMapper> {

    public ProfileService(ProfileRepository repository, ProfileMapper mapper, ProfileDTO profileDTO) {
        super(repository, mapper, profileDTO);
    }
}

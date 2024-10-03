package com.bank.profile.service.notgenerics;

import com.bank.profile.dto.ProfileDTO;
import com.bank.profile.mappers.exact.ProfileMapper;
import com.bank.profile.repository.exact.ProfileRepository;
import com.bank.profile.service.interfaces.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public List<ProfileDTO> getAllProfiles() {
        return profileMapper.toDtoList(profileRepository.findAll());
    }

    @Override
    public ProfileDTO getProfileById(Long id) {
        return profileMapper.toDto(profileRepository.findById(id).orElseThrow());
    }

    @Override
    public ProfileDTO addOrUpdateNewProfile(ProfileDTO profileDTO) {
        return profileMapper.toDto(profileRepository.save(profileMapper.toEntity(profileDTO)));
    }
}

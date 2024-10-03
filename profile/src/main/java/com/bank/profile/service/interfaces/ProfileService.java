package com.bank.profile.service.interfaces;

import com.bank.profile.dto.ProfileDTO;

import java.util.List;

public interface ProfileService {
    List<ProfileDTO> getAllProfiles();
    ProfileDTO getProfileById(Long id);
    ProfileDTO addOrUpdateNewProfile(ProfileDTO profileDTO);
}

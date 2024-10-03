package com.bank.profile.service.interfaces;

import com.bank.profile.dto.RegistrationDTO;

import java.util.List;

public interface RegistrationService {
    List<RegistrationDTO> getAllRegistrations();
    RegistrationDTO getRegistrationById(Long id);
    RegistrationDTO addOrUpdateNewRegistration(RegistrationDTO registrationDTO);
}

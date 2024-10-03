package com.bank.profile.service.notgenerics;

import com.bank.profile.dto.RegistrationDTO;
import com.bank.profile.mappers.exact.RegistrationMapper;
import com.bank.profile.repository.exact.RegistrationRepository;
import com.bank.profile.service.interfaces.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;

    @Override
    public List<RegistrationDTO> getAllRegistrations() {
        return registrationMapper.toDtoList(registrationRepository.findAll());
    }

    @Override
    public RegistrationDTO getRegistrationById(Long id) {
        return registrationMapper.toDto(registrationRepository.findById(id).orElseThrow());
    }

    @Override
    public RegistrationDTO addOrUpdateNewRegistration(RegistrationDTO registrationDTO) {
        return registrationMapper.toDto(registrationRepository.save(registrationMapper.toEntity(registrationDTO)));
    }
}

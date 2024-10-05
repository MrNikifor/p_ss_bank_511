package com.bank.profile.service;

import com.bank.profile.dto.RegistrationDTO;
import com.bank.profile.entity.Registration;
import com.bank.profile.mappers.RegistrationMapper;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.service.generics.AbstractBaseCrudService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService extends AbstractBaseCrudService<Registration, RegistrationDTO, RegistrationRepository, RegistrationMapper> {
    public RegistrationService(RegistrationRepository repository, RegistrationMapper mapper) {
        super(repository, mapper);
    }
}

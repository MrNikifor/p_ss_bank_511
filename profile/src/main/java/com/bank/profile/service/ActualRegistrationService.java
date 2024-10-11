package com.bank.profile.service;

import com.bank.profile.dto.ActualRegistrationDTO;
import com.bank.profile.entity.ActualRegistration;
import com.bank.profile.mappers.ActualRegistrationMapper;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.service.generics.AbstractBaseCrudService;
import com.bank.profile.validators.EntityValidator;
import org.springframework.stereotype.Service;

@Service
public class ActualRegistrationService extends AbstractBaseCrudService<ActualRegistration, ActualRegistrationDTO, ActualRegistrationRepository, ActualRegistrationMapper> {

    public ActualRegistrationService(ActualRegistrationRepository repository, ActualRegistrationMapper mapper, ActualRegistrationDTO actualRegistrationDTO, EntityValidator<ActualRegistration> entityValidator) {
        super(repository, mapper, actualRegistrationDTO, entityValidator);
    }
}

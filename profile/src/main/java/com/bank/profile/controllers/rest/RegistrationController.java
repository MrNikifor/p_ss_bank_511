package com.bank.profile.controllers.rest;

import com.bank.profile.controllers.rest.generics.AbstractController;
import com.bank.profile.dto.RegistrationDTO;
import com.bank.profile.entity.exact.Registration;
import com.bank.profile.mappers.RegistrationMapper;
import com.bank.profile.service.RegistrationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController extends AbstractController<Registration, RegistrationDTO, RegistrationService, RegistrationMapper> {
    public RegistrationController(RegistrationService service, RegistrationMapper mapper) {
        super(service, mapper);
    }
}

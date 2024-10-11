package com.bank.profile.controllers.rest;

import com.bank.profile.controllers.rest.generics.AbstractController;
import com.bank.profile.dto.ActualRegistrationDTO;
import com.bank.profile.entity.ActualRegistration;
import com.bank.profile.mappers.ActualRegistrationMapper;
import com.bank.profile.service.ActualRegistrationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actualregistration")
public class ActualRegistrationController extends AbstractController<ActualRegistration, ActualRegistrationDTO, ActualRegistrationService, ActualRegistrationMapper> {

    public ActualRegistrationController(ActualRegistrationService service, ActualRegistrationMapper mapper) {
        super(service, mapper);
    }
}

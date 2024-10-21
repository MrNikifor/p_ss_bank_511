package com.bank.profile.controllers.rest;

import com.bank.profile.controllers.rest.generics.AbstractController;
import com.bank.profile.dto.PassportDTO;
import com.bank.profile.entity.Passport;
import com.bank.profile.mappers.PassportMapper;
import com.bank.profile.service.PassportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passport")
public class PassportController extends AbstractController<Passport, PassportDTO, PassportService, PassportMapper> {

    public PassportController(PassportService service, PassportMapper mapper) {
        super(service, mapper);
    }
}

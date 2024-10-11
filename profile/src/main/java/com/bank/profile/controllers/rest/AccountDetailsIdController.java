package com.bank.profile.controllers.rest;

import com.bank.profile.controllers.rest.generics.AbstractController;
import com.bank.profile.dto.AccountDetailsIdDTO;
import com.bank.profile.entity.AccountDetailsId;
import com.bank.profile.mappers.AccountDetailsIdMapper;
import com.bank.profile.service.AccountDetailsIdService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accountdetailsid")
public class AccountDetailsIdController extends AbstractController<
        AccountDetailsId,
        AccountDetailsIdDTO,
        AccountDetailsIdService,
        AccountDetailsIdMapper> {

    public AccountDetailsIdController(AccountDetailsIdService service, AccountDetailsIdMapper mapper) {
        super(service, mapper);
    }
}

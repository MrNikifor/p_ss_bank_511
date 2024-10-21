package com.bank.profile.service;

import com.bank.profile.dto.AccountDetailsIdDTO;
import com.bank.profile.entity.AccountDetailsId;
import com.bank.profile.mappers.AccountDetailsIdMapper;
import com.bank.profile.repository.AccountDetailsIdRepository;
import com.bank.profile.service.generics.AbstractBaseCrudService;
import com.bank.profile.validators.EntityValidator;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsIdService extends AbstractBaseCrudService<AccountDetailsId, AccountDetailsIdDTO, AccountDetailsIdRepository, AccountDetailsIdMapper> {

    public AccountDetailsIdService(AccountDetailsIdRepository repository, AccountDetailsIdMapper mapper, AccountDetailsIdDTO accountDetailsIdDTO, EntityValidator<AccountDetailsId> entityValidator) {
        super(repository, mapper, accountDetailsIdDTO, entityValidator);
    }
}

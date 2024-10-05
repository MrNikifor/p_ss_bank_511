package com.bank.profile.service;

import com.bank.profile.dto.PassportDTO;
import com.bank.profile.entity.Passport;
import com.bank.profile.mappers.PassportMapper;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.service.generics.AbstractBaseCrudService;
import org.springframework.stereotype.Service;

@Service
public class PassportService extends AbstractBaseCrudService<Passport, PassportDTO, PassportRepository, PassportMapper> {

    public PassportService(PassportRepository repository, PassportMapper mapper) {
        super(repository, mapper);
    }
}

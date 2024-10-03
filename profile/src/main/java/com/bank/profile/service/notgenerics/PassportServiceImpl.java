package com.bank.profile.service.notgenerics;

import com.bank.profile.dto.PassportDTO;
import com.bank.profile.mappers.exact.PassportMapper;
import com.bank.profile.repository.exact.PassportRepository;
import com.bank.profile.service.interfaces.PassportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;
    private final PassportMapper passportMapper;

    @Override
    public List<PassportDTO> getAllPassports() {
        return passportMapper.toDtoList(passportRepository.findAll());
    }

    @Override
    public PassportDTO getPassportById(Long id) {
        return passportMapper.toDto(passportRepository.findById(id).orElseThrow());
    }

    @Override
    public PassportDTO addOrUpdateNewPassport(PassportDTO passportDTO) {
        return passportMapper.toDto(passportRepository.save(passportMapper.toEntity(passportDTO)));
    }
}

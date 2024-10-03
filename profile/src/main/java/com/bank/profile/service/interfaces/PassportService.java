package com.bank.profile.service.interfaces;

import com.bank.profile.dto.PassportDTO;

import java.util.List;

public interface PassportService {
    List<PassportDTO> getAllPassports();
    PassportDTO getPassportById(Long id);
    PassportDTO addOrUpdateNewPassport(PassportDTO passportDTO);
}

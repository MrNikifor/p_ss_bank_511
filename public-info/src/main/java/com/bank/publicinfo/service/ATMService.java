package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.ATMDto;

import java.util.List;

public interface ATMService {
    ATMDto createATM(ATMDto atmDTO);
    ATMDto getATMById(Long id);
    List<ATMDto> getAllATMs();
    ATMDto updateATM(ATMDto atmDTO);
    void deleteATM(Long id);
    boolean isATMAvailable(Long id);
}

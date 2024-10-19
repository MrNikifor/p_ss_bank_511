package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BankDetailsDto;

import java.util.List;

public interface BankDetailsService {
    BankDetailsDto createBankDetails(BankDetailsDto bankDetailsDTO);
    BankDetailsDto getBankDetailsById(Long id);
    List<BankDetailsDto> getAllBankDetails();
    BankDetailsDto updateBankDetails(BankDetailsDto bankDetailsDTO);
    void deleteBankDetails(Long id);
}


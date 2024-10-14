package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BankDetailsDTO;

import java.util.List;

public interface BankDetailsService {
    BankDetailsDTO createBankDetails(BankDetailsDTO bankDetailsDTO);
    BankDetailsDTO getBankDetailsById(Long id);
    List<BankDetailsDTO> getAllBankDetails();
    BankDetailsDTO updateBankDetails(BankDetailsDTO bankDetailsDTO);
    void deleteBankDetails(Long id);
}


package com.bank.account.service;

import com.bank.account.dto.AccountDetailsDTO;

import java.util.List;

public interface AccountDetailsService {
    AccountDetailsDTO saveAccountDetails(AccountDetailsDTO accountDetailsDTO);
    AccountDetailsDTO updateAccountDetails(AccountDetailsDTO accountDetailsDTO);
    AccountDetailsDTO getAccountDetails(Long id);
    List<AccountDetailsDTO> getAllAccountDetails();
    void deleteAccountDetails(AccountDetailsDTO accountDetailsDTO);
}

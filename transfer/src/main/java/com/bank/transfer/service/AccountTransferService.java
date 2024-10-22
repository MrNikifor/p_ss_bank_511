package com.bank.transfer.service;

import com.bank.transfer.dto.AccountTransferDTO;

import java.util.List;

public interface AccountTransferService {

    List<AccountTransferDTO> getAllAccountTransfers();

    AccountTransferDTO getAccountTransferById(Long id);

    AccountTransferDTO createAccountTransfer(AccountTransferDTO accountTransferDTO);

    AccountTransferDTO updateAccountTransfer(AccountTransferDTO accountTransferDTO, Long id);

    AccountTransferDTO deleteAccountTransfer(Long id);
}

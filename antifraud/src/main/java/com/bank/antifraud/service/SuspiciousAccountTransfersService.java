package com.bank.antifraud.service;

import com.bank.antifraud.dto.AccountTransfersDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SuspiciousAccountTransfersService {
    @Transactional
    List<AccountTransfersDTO> getListSAccountTransfers();

    @Transactional
    AccountTransfersDTO saveAccount(AccountTransfersDTO newAccountTransfers);

    @Transactional
    AccountTransfersDTO updateAccount(AccountTransfersDTO updateTrDTO, Long id);

    void deleteAccount(Long id);

    AccountTransfersDTO getAccountTransfer(Long id);
}

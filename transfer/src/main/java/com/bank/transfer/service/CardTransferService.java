package com.bank.transfer.service;

import com.bank.transfer.dto.CardTransferDTO;

import java.util.List;

public interface CardTransferService {

    List<CardTransferDTO> getAllCardTransfers();

    CardTransferDTO getCardTransferById(Long id);

    CardTransferDTO createCardTransfer(CardTransferDTO cardTransferDTO);

    CardTransferDTO updateCardTransfer(CardTransferDTO cardTransferDTO, Long id);

    CardTransferDTO deleteCardTransfer(Long id);
}

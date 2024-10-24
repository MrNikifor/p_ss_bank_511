package com.bank.antifraud.service;

import com.bank.antifraud.dto.CardTransfersDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SuspiciousCardTransfersService {

    @Transactional
    List<CardTransfersDTO> getListSCardTransfers();

    @Transactional
    CardTransfersDTO saveCard(CardTransfersDTO newCardTransfers);

    @Transactional
    CardTransfersDTO updateCard(CardTransfersDTO updateTrDTO, Long id);

    void delete(Long id);

    CardTransfersDTO getCardTransfer(Long id);
}

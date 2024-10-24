package com.bank.antifraud.service;

import com.bank.antifraud.dto.CardTransfersDTO;
import com.bank.antifraud.exception.EntityNotFoundException;
import com.bank.antifraud.mapper.CardTransferMapper;
import com.bank.antifraud.model.SuspiciousCardTransfers;
import com.bank.antifraud.repository.SuspiciousCardTransfersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class SuspiciousCardTransfersServiceImp implements SuspiciousCardTransfersService {

    private final SuspiciousCardTransfersRepository sctRepository;
    private final CardTransferMapper cardTransferMapper = CardTransferMapper.INSTANCE;

    @Autowired
    public SuspiciousCardTransfersServiceImp(SuspiciousCardTransfersRepository sctRepository) {
        this.sctRepository = sctRepository;
    }

    @Override
    public List<CardTransfersDTO> getListSCardTransfers() {

        log.info("Getting all card transfers");
        List<SuspiciousCardTransfers> accountTransfersListEntity = sctRepository.findAll();

        return cardTransferMapper.toDTOList(accountTransfersListEntity);

    }

    @Override
    @Transactional
    public CardTransfersDTO saveCard(CardTransfersDTO newCardTransfers) {

        log.info("Getting account transfer by id: {}", newCardTransfers);
        SuspiciousCardTransfers cardTransfers = cardTransferMapper.toEntity(newCardTransfers);
        SuspiciousCardTransfers accountSave = sctRepository.save(cardTransfers);

        return cardTransferMapper.toDTO(accountSave);

    }

    @Override
    @Transactional
    public CardTransfersDTO updateCard(CardTransfersDTO updateTrDTO, Long id) {

        log.info("Creating account transfer: {}", updateTrDTO);
        SuspiciousCardTransfers accountTransfers = sctRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AccountTransfers not found",
                        "CardTransferMapperServiceImp.update"));
        cardTransferMapper.updateEntityFromDTO(updateTrDTO, accountTransfers);
        SuspiciousCardTransfers updateAccount = sctRepository.save(accountTransfers);

        return cardTransferMapper.toDTO(updateAccount);
    }

    @Override
    public void delete(Long id) {

        log.info("Deleting account transfer: {}", id);
        try {
            sctRepository.deleteById(id);
        } catch (EntityNotFoundException ex) {

            throw new EntityNotFoundException(
                    "An object with this ID was not found for deletion, ID: " + id,
                    "AccountTransferServiceImpl.deleteAccountTransfer");
        }
    }

    @Override
    public CardTransfersDTO getCardTransfer(Long id) {

        log.info("Getting account transfer by id: {}", id);
        SuspiciousCardTransfers accountTransferEntity = sctRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("An object with this ID was not found, ID: " + id,
                                "AccountTransferServiceImpl.getAccountTransferById"));

        return cardTransferMapper.toDTO(accountTransferEntity);
    }
}


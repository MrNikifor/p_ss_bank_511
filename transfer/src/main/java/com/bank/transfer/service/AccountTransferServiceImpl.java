package com.bank.transfer.service;

import com.bank.transfer.dto.AccountTransferDTO;
import com.bank.transfer.entity.AccountTransferEntity;
import com.bank.transfer.exception.EntityAlreadyExistsException;
import com.bank.transfer.exception.EntityNotFoundException;
import com.bank.transfer.exception.RequiredFieldEmptyException;
import com.bank.transfer.exception.UniqueFieldEmptyException;
import com.bank.transfer.mapper.AccountTransferMapper;
import com.bank.transfer.repository.AccountTransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AccountTransferServiceImpl implements AccountTransferService {

    private final AccountTransferRepository accountTransferRepository;

    private final AccountTransferMapper accountTransferMapper = AccountTransferMapper.INSTANCE;

    @Autowired
    public AccountTransferServiceImpl(AccountTransferRepository accountTransferRepository) {
        this.accountTransferRepository = accountTransferRepository;
    }

    @Override
    public List<AccountTransferDTO> getAllAccountTransfers() {

        log.info("Getting all account transfers");

        List<AccountTransferEntity> accountTransfersListEntity = accountTransferRepository.findAll();

        return accountTransferMapper.toDTOList(accountTransfersListEntity);
    }

    @Override
    public AccountTransferDTO getAccountTransferById(Long id) {

        log.info("Getting account transfer by id: {}", id);

        AccountTransferEntity accountTransferEntity = accountTransferRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("An object with this ID was not found, ID: " + id,
                                "AccountTransferServiceImpl.getAccountTransferById"));

        return accountTransferMapper.toDTO(accountTransferEntity);
    }

    @Transactional
    @Override
    public AccountTransferDTO createAccountTransfer(AccountTransferDTO accountTransferDTO) {

        log.info("Creating account transfer: {}", accountTransferDTO);

        if (accountTransferDTO.getAccountNumber() == null) {

            throw new UniqueFieldEmptyException("An empty unique field for AccountTransferEntity was passed",
                    "AccountTransferServiceImpl.createAccountTransfer");

        } else if (uniqueAccountTransferEntity(accountTransferDTO) != null) {

            throw new EntityAlreadyExistsException("An object with the same account number already exists",
                    "AccountTransferServiceImpl.createAccountTransfer");

        } else if (accountTransferDTO.getAmount() == null || accountTransferDTO.getAccountDetailsId() == null) {

            throw new RequiredFieldEmptyException("An empty required field has been passed",
                    "CardTransferServiceImpl.createCardTransfer");

        }

        AccountTransferEntity accountTransferEntity = accountTransferMapper.toEntity(accountTransferDTO);
        AccountTransferEntity savedAccountTransferEntity = accountTransferRepository.save(accountTransferEntity);

        return accountTransferMapper.toDTO(savedAccountTransferEntity);
    }

    @Transactional
    @Override
    public AccountTransferDTO updateAccountTransfer(AccountTransferDTO accountTransferDTO, Long id) {

        log.info("Updating account transfer: {}", accountTransferDTO);

        AccountTransferEntity existingAccountTransferEntity = accountTransferRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "An object with this ID was not found for editing, ID: " + id,
                                "AccountTransferServiceImpl.updateAccountTransfer"));

        AccountTransferEntity checkUniqueEntity = uniqueAccountTransferEntity(accountTransferDTO);

        if (checkUniqueEntity != null && !checkUniqueEntity.getId().equals(id)) {
            throw new EntityAlreadyExistsException(
                    "An object with the same account number already exists with a different ID",
                    "AccountTransferServiceImpl.updateAccountTransfer");
        }

        accountTransferMapper.updateEntityFromDTO(accountTransferDTO, existingAccountTransferEntity);

        AccountTransferEntity savedAccountTransferEntity =
                accountTransferRepository.save(existingAccountTransferEntity);

        return accountTransferMapper.toDTO(savedAccountTransferEntity);
    }

    @Transactional
    @Override
    public AccountTransferDTO deleteAccountTransfer(Long id) {

        log.info("Deleting account transfer: {}", id);

        AccountTransferEntity existsAccountTransfer = accountTransferRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "An object with this ID was not found for deletion, ID: " + id,
                        "AccountTransferServiceImpl.deleteAccountTransfer"));

        accountTransferRepository.deleteById(id);

        return accountTransferMapper.toDTO(existsAccountTransfer);
    }

    private AccountTransferEntity uniqueAccountTransferEntity(AccountTransferDTO accountTransferDTO) {

        Optional<AccountTransferEntity> accountTransfer
                = accountTransferRepository.findByAccountNumber(accountTransferDTO.getAccountNumber());

        return accountTransfer.orElse(null);
    }
}

package com.bank.account.service;

import com.bank.account.dto.AccountDetailsDTO;
import com.bank.account.entity.AccountDetails;
import com.bank.account.exception.AccountNotFoundException;
import com.bank.account.mapper.AccountDetailsMapper;
import com.bank.account.repository.AccountDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountDetailsServiceImpl implements AccountDetailsService {
    private final AccountDetailsRepository accountDetailsRepository;
    private final AccountDetailsMapper accountDetailsMapper;

    @Override
    public AccountDetailsDTO saveAccountDetails(AccountDetailsDTO accountDetailsDTO) {
        try {
            accountDetailsRepository.save(accountDetailsMapper.INSTANCE.toEntity(accountDetailsDTO));
            log.info("Сохранены детали счёта {}", accountDetailsDTO);
            return accountDetailsDTO;
        } catch (Exception e) {
            log.error("При сохранении деталей счёта произошла ошибка: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public AccountDetailsDTO updateAccountDetails(AccountDetailsDTO accountDetailsDTO) {
        try {
            AccountDetails accountDetails = accountDetailsMapper.INSTANCE.toEntity(accountDetailsDTO);
            accountDetailsRepository.findById(accountDetails.getId())
                    .orElseThrow(() -> new AccountNotFoundException(accountDetails.getId()));
            accountDetailsRepository.save(accountDetails);
            log.info("Обновлены детали счёта {}", accountDetails);
            return accountDetailsDTO;
        } catch (Exception e) {
            log.error("При обновлении деталей счёта произошла ошибка: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public AccountDetailsDTO getAccountDetails(Long id) {
        return accountDetailsMapper
                .INSTANCE.toDTO(accountDetailsRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id)));
    }

    @Override
    public List<AccountDetailsDTO> getAllAccountDetails() {
        try {
            return accountDetailsRepository.findAll()
                    .stream()
                    .map(accountDetailsMapper.INSTANCE::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Произошла ошибка при выводе списка всех счетов: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteAccountDetails(AccountDetailsDTO accountDetailsDTO) {
        accountDetailsRepository.findById(accountDetailsDTO.getId())
                .orElseThrow(() -> new AccountNotFoundException(accountDetailsDTO.getId()));
        accountDetailsRepository.delete(accountDetailsMapper.INSTANCE.toEntity(accountDetailsDTO));
        log.info("Удалён счёт {}", accountDetailsDTO);
    }
}

package com.bank.account.service;

import com.bank.account.entity.AccountDetails;
import com.bank.account.exception.AccountNotFoundException;
import com.bank.account.repository.AccountDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountDetailsServiceImpl implements AccountDetailsService {
    private final AccountDetailsRepository accountDetailsRepository;

    @Override
    public AccountDetails saveAccountDetails(AccountDetails accountDetails) {
        try {
            accountDetailsRepository.save(accountDetails);
            log.info("Сохранены детали счёта {}", accountDetails);
            return accountDetails;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public AccountDetails updateAccountDetails(AccountDetails accountDetails) {
        try {
            accountDetailsRepository.findById(accountDetails.getId())
                    .orElseThrow(() -> new AccountNotFoundException(accountDetails.getId()));
            accountDetailsRepository.save(accountDetails);
            log.info("Обновлены детали счёта {}", accountDetails);
            return accountDetails;
        } catch (AccountNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public AccountDetails getAccountDetails(Long id) {
        return accountDetailsRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    @Override
    public List<AccountDetails> getAllAccountDetails() {
        try {
            return accountDetailsRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteAccountDetails(AccountDetails accountDetails) {
        accountDetailsRepository.findById(accountDetails.getId())
                .orElseThrow(() -> new AccountNotFoundException(accountDetails.getId()));
        accountDetailsRepository.delete(accountDetails);
        log.info("Удалён счёт {}", accountDetails);
    }
}

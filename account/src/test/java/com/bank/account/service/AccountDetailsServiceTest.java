package com.bank.account.service;

import com.bank.account.entity.AccountDetails;
import com.bank.account.exception.AccountNotFoundException;
import com.bank.account.repository.AccountDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountDetailsServiceTest {
    @Mock
    private AccountDetailsRepository accountDetailsRepository;
    @InjectMocks
    private AccountDetailsServiceImpl accountDetailsService;
    private AccountDetails accountDetails;

    @BeforeEach
    void setUpAccountDetails() {
        MockitoAnnotations.openMocks(this);
        accountDetails = new AccountDetails();
        accountDetails.setId(1L);
        accountDetails.setPassportId(1L);
        accountDetails.setAccountNumber(1L);
        accountDetails.setBankDetailsId(1L);
        accountDetails.setMoney(BigDecimal.valueOf(1234567.00));
        accountDetails.setNegativeBalance(false);
        accountDetails.setProfileId(1L);
    }

    @Test
    void saveAccountDetails() {
        when(accountDetailsRepository.save(accountDetails)).thenReturn(accountDetails);

        AccountDetails testAccountDetails = accountDetailsService.saveAccountDetails(accountDetails);

        assertNotNull(testAccountDetails);
        assertEquals(testAccountDetails.getId(), accountDetails.getId());
        verify(accountDetailsRepository, times(1)).save(accountDetails);
    }

    @Test
    void saveAccountDetailsWithException() {
        Exception expectedException = new RuntimeException("Ошибка сохранения в репозитории");
        doThrow(expectedException).when(accountDetailsRepository).save(accountDetails);

        assertThrows(RuntimeException.class, () -> accountDetailsService.saveAccountDetails(accountDetails));
        verify(accountDetailsRepository, times(1)).save(accountDetails);
    }

    @Test
    void updateAccountDetails() {
        when(accountDetailsRepository.findById(accountDetails.getId())).thenReturn(Optional.of(accountDetails));
        when(accountDetailsRepository.save(accountDetails)).thenReturn(accountDetails);

        AccountDetails updatedAccountDetails = accountDetailsService.updateAccountDetails(accountDetails);

        assertEquals(accountDetails, updatedAccountDetails);
        verify(accountDetailsRepository, times(1)).findById(accountDetails.getId());
        verify(accountDetailsRepository, times(1)).save(accountDetails);
    }

    @Test
    void updateAccountDetails_notFound() {
        when(accountDetailsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountDetailsService.updateAccountDetails(accountDetails));
        verify(accountDetailsRepository, times(1)).findById(1L);
        verify(accountDetailsRepository, never()).save(accountDetails);
    }

    @Test
    void updateAccountDetailsWithException() {
        when(accountDetailsRepository.findById(1L)).thenReturn(Optional.of(accountDetails));
        Exception expectedException = new RuntimeException("Ошибка обновления данных в репозитории");
        doThrow(expectedException).when(accountDetailsRepository).save(accountDetails);

        assertThrows(RuntimeException.class, () -> accountDetailsService.updateAccountDetails(accountDetails));
        verify(accountDetailsRepository, times(1)).findById(1L);
        verify(accountDetailsRepository, times(1)).save(accountDetails);
    }


    @Test
    void getAccountDetails() {
        when(accountDetailsRepository.findById(1L)).thenReturn(Optional.of(accountDetails));

        AccountDetails retrievedAccountDetails = accountDetailsService.getAccountDetails(1L);

        assertEquals(accountDetails, retrievedAccountDetails);
        verify(accountDetailsRepository, times(1)).findById(1L);
    }

    @Test
    void getAccountDetails_notFound() {
        when(accountDetailsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountDetailsService.getAccountDetails(1L));
        verify(accountDetailsRepository, times(1)).findById(1L);
    }

    @Test
    void getAllAccountDetails() {
        when(accountDetailsRepository.findAll()).thenReturn(List.of(accountDetails));

        List<AccountDetails> allAccountDetails = accountDetailsService.getAllAccountDetails();

        assertEquals(1, allAccountDetails.size());
        verify(accountDetailsRepository, times(1)).findAll();
    }

    @Test
    void getAllAccountDetailsWithEmptyList() {
        when(accountDetailsRepository.findAll()).thenReturn(Collections.emptyList());

        List<AccountDetails> accountDetails = accountDetailsService.getAllAccountDetails();

        assertEquals(0, accountDetails.size());
        verify(accountDetailsRepository, times(1)).findAll();
    }

    @Test
    void getAllAccountDetailsWithException() {
        Exception expectedException = new RuntimeException("Ошибка получения данных из репозитория");
        doThrow(expectedException).when(accountDetailsRepository).findAll();

        assertThrows(RuntimeException.class, () -> accountDetailsService.getAllAccountDetails());
        verify(accountDetailsRepository, times(1)).findAll();
    }

    @Test
    void deleteAccountDetails() {
        when(accountDetailsRepository.findById(1L)).thenReturn(Optional.of(accountDetails));

        accountDetailsService.deleteAccountDetails(accountDetails);

        verify(accountDetailsRepository, times(1)).findById(1L);
        verify(accountDetailsRepository, times(1)).delete(accountDetails);
    }

    @Test
    void deleteAccountDetails_notFound() {
        when(accountDetailsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountDetailsService.deleteAccountDetails(accountDetails));
        verify(accountDetailsRepository, times(1)).findById(1L);
        verify(accountDetailsRepository, never()).delete(accountDetails);
    }
}
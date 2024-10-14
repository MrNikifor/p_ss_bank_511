package com.bank.transfer.service;

import com.bank.transfer.dto.AccountTransferDTO;
import com.bank.transfer.entity.AccountTransferEntity;
import com.bank.transfer.exception.EntityAlreadyExistsException;
import com.bank.transfer.exception.EntityNotFoundException;
import com.bank.transfer.exception.RequiredFieldEmptyException;
import com.bank.transfer.exception.UniqueFieldEmptyException;
import com.bank.transfer.repository.AccountTransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountTransferServiceImplTest {

    @Mock
    private AccountTransferRepository accountTransferRepository;

    @InjectMocks
    private AccountTransferServiceImpl accountTransferService;

    private AccountTransferEntity accountTransferEntity;

    private AccountTransferDTO accountTransferDTO;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        accountTransferEntity = AccountTransferEntity.builder()
                .id(1L)
                .accountNumber(1122334455L)
                .amount(BigDecimal.valueOf(19000.00))
                .purpose("Test1")
                .accountDetailsId(110L)
                .build();

        accountTransferDTO = AccountTransferDTO.builder()
                .id(1L)
                .accountNumber(1122334455L)
                .amount(BigDecimal.valueOf(19000.00))
                .purpose("Test1")
                .accountDetailsId(110L)
                .build();
    }

    @Test
    public void testGetAllAccountTransfers_ShouldReturnListOfAccountTransferDTO() {

        when(accountTransferRepository.findAll()).thenReturn(List.of(accountTransferEntity));

        List<AccountTransferDTO> allAccountTransferDTO = accountTransferService.getAllAccountTransfers();

        assertNotNull(allAccountTransferDTO);
        assertEquals(allAccountTransferDTO.size(), 1);
        assertEquals(1122334455L, allAccountTransferDTO.get(0).getAccountNumber());

        verify(accountTransferRepository, times(1)).findAll();
    }

    @Test
    public void testGetAccountTransferById_ShouldReturnAccountTransferDTO() {

        when(accountTransferRepository.findById(1L)).thenReturn(Optional.of(accountTransferEntity));

        AccountTransferDTO result = accountTransferService.getAccountTransferById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1122334455L, result.getAccountNumber());

        verify(accountTransferRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAccountTransferById_ShouldThrowEntityNotFoundException() {

        when(accountTransferRepository.findById(12L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> accountTransferService.getAccountTransferById(12L));

        verify(accountTransferRepository, times(1)).findById(12L);
    }

    @Test
    public void testCreateAccountTransfer_ShouldCreateAccountTransferDTO() {

        when(accountTransferRepository.findByAccountNumber(1122334455L)).thenReturn(Optional.empty());
        when(accountTransferRepository.save(any(AccountTransferEntity.class))).thenReturn(accountTransferEntity);

        AccountTransferDTO result = accountTransferService.createAccountTransfer(accountTransferDTO);

        assertNotNull(result);
        assertEquals(1122334455L, result.getAccountNumber());
    }

    @Test
    public void testCreateAccountTransfer_ShouldThrowUniqueFieldEmptyException() {

        accountTransferDTO.setAccountNumber(null);

        assertThrows(UniqueFieldEmptyException.class,
                () -> accountTransferService.createAccountTransfer(accountTransferDTO));
    }

    @Test
    public void testCreateAccountTransfer_ShouldThrowEntityAlreadyExistsException() {

        when(accountTransferRepository.findByAccountNumber(1122334455L)).thenReturn(Optional.of(accountTransferEntity));

        assertThrows(EntityAlreadyExistsException.class,
                () -> accountTransferService.createAccountTransfer(accountTransferDTO));
    }

    @Test
    public void testCreateAccountTransfer_ShouldThrowRequiredFieldEmptyException() {

        accountTransferDTO.setAmount(null);

        assertThrows(RequiredFieldEmptyException.class,
                () -> accountTransferService.createAccountTransfer(accountTransferDTO));
    }

    @Test
    public void testUpdateAccountTransfer_ShouldUpdateAccountTransferDTO() {

        when(accountTransferRepository.findById(1L)).thenReturn(Optional.of(accountTransferEntity));
        when(accountTransferRepository.findByAccountNumber(1122334455L)).thenReturn(Optional.empty());
        when(accountTransferRepository.save(any(AccountTransferEntity.class))).thenReturn(accountTransferEntity);

        AccountTransferDTO result = accountTransferService.updateAccountTransfer(accountTransferDTO, 1L);

        assertNotNull(result);
        assertEquals(1122334455L, result.getAccountNumber());
    }

    @Test
    public void testUpdateAccountTransfer_ShouldThrowEntityNotFoundException() {

        when(accountTransferRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> accountTransferService.updateAccountTransfer(accountTransferDTO, 1L));
    }

    @Test
    public void testUpdateAccountTransfer_ShouldThrowAlreadyExistsException() {

        AccountTransferEntity anotherEntity = AccountTransferEntity.builder()
                .id(2L)
                .accountNumber(1122334455L)
                .amount(BigDecimal.valueOf(29000.00))
                .build();

        when(accountTransferRepository.findById(1L)).thenReturn(Optional.of(accountTransferEntity));
        when(accountTransferRepository.findByAccountNumber(1122334455L)).thenReturn(Optional.of(anotherEntity));

        assertThrows(EntityAlreadyExistsException.class,
                () -> accountTransferService.updateAccountTransfer(accountTransferDTO, 1L));
    }

    @Test
    public void testDeleteAccountTransfer_ShouldDeleteAccountTransferDTO() {

        when(accountTransferRepository.findById(1L)).thenReturn(Optional.of(accountTransferEntity));

        AccountTransferDTO result = accountTransferService.deleteAccountTransfer(1L);

        assertNotNull(result);
        assertEquals(1122334455L, result.getAccountNumber());

        verify(accountTransferRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteAccountTransfer_ShouldThrowEntityNotFoundException() {

        when(accountTransferRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> accountTransferService.deleteAccountTransfer(1L));
    }
}

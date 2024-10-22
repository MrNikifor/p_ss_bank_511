package com.bank.transfer.service;

import com.bank.transfer.dto.PhoneTransferDTO;
import com.bank.transfer.entity.PhoneTransferEntity;
import com.bank.transfer.exception.EntityNotFoundException;
import com.bank.transfer.exception.RequiredFieldEmptyException;
import com.bank.transfer.repository.PhoneTransferRepository;
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

public class PhoneTransferServiceImplTest {

    @Mock
    private PhoneTransferRepository phoneTransferRepository;

    @InjectMocks
    private PhoneTransferServiceImpl phoneTransferService;

    private PhoneTransferEntity phoneTransferEntity;

    private PhoneTransferDTO phoneTransferDTO;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        phoneTransferEntity = PhoneTransferEntity.builder()
                .id(1L)
                .phoneNumber(89952340011L)
                .amount(BigDecimal.valueOf(1000.00))
                .purpose("Test1")
                .accountDetailsId(111L)
                .build();

        phoneTransferDTO = PhoneTransferDTO.builder()
                .id(1L)
                .phoneNumber(89952340011L)
                .amount(BigDecimal.valueOf(1000.00))
                .purpose("Test1")
                .accountDetailsId(111L)
                .build();
    }

    @Test
    public void testGetAllPhoneTransfers_ShouldReturnListOfPhoneTransferDTO() {

        when(phoneTransferRepository.findAll()).thenReturn(List.of(phoneTransferEntity));

        List<PhoneTransferDTO> result = phoneTransferService.getAllPhoneTransfers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(89952340011L, result.get(0).getPhoneNumber());

        verify(phoneTransferRepository, times(1)).findAll();
    }

    @Test
    public void testGetPhoneTransferById_ShouldReturnPhoneTransferDTO() {

        when(phoneTransferRepository.findById(1L)).thenReturn(Optional.of(phoneTransferEntity));

        PhoneTransferDTO result = phoneTransferService.getPhoneTransferById(1L);

        assertNotNull(result);
        assertEquals(89952340011L, result.getPhoneNumber());

        verify(phoneTransferRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetPhoneTransferById_ShouldThrowEntityNotFoundException() {

        when(phoneTransferRepository.findById(12L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> phoneTransferService.getPhoneTransferById(12L));

        verify(phoneTransferRepository, times(1)).findById(12L);
    }

    @Test
    public void testCreatePhoneTransfer_ShouldCreatePhoneTransfer() {

        when(phoneTransferRepository.save(any(PhoneTransferEntity.class))).thenReturn(phoneTransferEntity);

        PhoneTransferDTO result = phoneTransferService.createPhoneTransfer(phoneTransferDTO);

        assertNotNull(result);
        assertEquals(89952340011L, result.getPhoneNumber());

        verify(phoneTransferRepository, times(1)).save(any(PhoneTransferEntity.class));
    }

    @Test
    public void testCreatePhoneTransfer_ShouldThrowRequiredFieldEmptyException() {

        phoneTransferDTO.setPhoneNumber(null);

        assertThrows(RequiredFieldEmptyException.class,
                () -> phoneTransferService.createPhoneTransfer(phoneTransferDTO));

        verify(phoneTransferRepository, times(0)).save(any(PhoneTransferEntity.class));
    }

    @Test
    public void testUpdatePhoneTransfer_ShouldUpdatePhoneTransfer() {

        when(phoneTransferRepository.findById(1L)).thenReturn(Optional.of(phoneTransferEntity));
        when(phoneTransferRepository.save(any(PhoneTransferEntity.class))).thenReturn(phoneTransferEntity);

        PhoneTransferDTO result = phoneTransferService.updatePhoneTransfer(phoneTransferDTO, 1L);

        assertNotNull(result);
        assertEquals(89952340011L, result.getPhoneNumber());

        verify(phoneTransferRepository, times(1)).save(any(PhoneTransferEntity.class));
    }

    @Test
    public void testUpdatePhoneTransfer_ShouldThrowEntityNotFoundException() {

        when(phoneTransferRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> phoneTransferService.updatePhoneTransfer(phoneTransferDTO, 1L));

        verify(phoneTransferRepository, times(0)).save(any(PhoneTransferEntity.class));
    }

    @Test
    public void testDeletePhoneTransfer_ShouldDeletePhoneTransfer() {

        when(phoneTransferRepository.findById(1L)).thenReturn(Optional.of(phoneTransferEntity));

        PhoneTransferDTO result = phoneTransferService.deletePhoneTransfer(1L);

        assertNotNull(result);
        assertEquals(89952340011L, result.getPhoneNumber());

        verify(phoneTransferRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeletePhoneTransfer_ShouldThrowEntityNotFoundException() {

        when(phoneTransferRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> phoneTransferService.deletePhoneTransfer(1L));

        verify(phoneTransferRepository, times(0)).deleteById(1L);
    }
}

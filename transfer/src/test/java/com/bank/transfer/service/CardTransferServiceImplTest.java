package com.bank.transfer.service;

import com.bank.transfer.dto.CardTransferDTO;
import com.bank.transfer.entity.CardTransferEntity;
import com.bank.transfer.exception.EntityAlreadyExistsException;
import com.bank.transfer.exception.EntityNotFoundException;
import com.bank.transfer.exception.RequiredFieldEmptyException;
import com.bank.transfer.exception.UniqueFieldEmptyException;
import com.bank.transfer.repository.CardTransferRepository;
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

public class CardTransferServiceImplTest {

    @Mock
    private CardTransferRepository cardTransferRepository;

    @InjectMocks
    private CardTransferServiceImpl cardTransferService;

    private CardTransferEntity cardTransferEntity;

    private CardTransferDTO cardTransferDTO;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        cardTransferEntity = CardTransferEntity.builder()
                .id(1L)
                .cardNumber(2203440098981212L)
                .amount(BigDecimal.valueOf(1000.00))
                .purpose("Test1")
                .accountDetailsId(100L)
                .build();

        cardTransferDTO = CardTransferDTO.builder()
                .id(1L)
                .cardNumber(2203440098981212L)
                .amount(BigDecimal.valueOf(1000.00))
                .purpose("Test1")
                .accountDetailsId(100L)
                .build();
    }

    @Test
    public void testGetAllCardTransfers_ShouldReturnListOfCardTransfersDTO() {

        when(cardTransferRepository.findAll()).thenReturn(List.of(cardTransferEntity));

        List<CardTransferDTO> allCardTransfers = cardTransferService.getAllCardTransfers();

        assertNotNull(allCardTransfers);
        assertEquals(1, allCardTransfers.size());
        assertEquals(2203440098981212L, allCardTransfers.get(0).getCardNumber());

        verify(cardTransferRepository, times(1)).findAll();
    }

    @Test
    public void testGetCardTransferById_ShouldReturnCardTransferDTO() {

        when(cardTransferRepository.findById(1L)).thenReturn(Optional.of(cardTransferEntity));

        CardTransferDTO result = cardTransferService.getCardTransferById(1L);

        assertNotNull(result);
        assertEquals(2203440098981212L, result.getCardNumber());

        verify(cardTransferRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCardTransferByIdNotFound_ShouldThrowEntityNotFoundException() {

        when(cardTransferRepository.findById(12L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> cardTransferService.getCardTransferById(12L));

        verify(cardTransferRepository, times(1)).findById(12L);
    }

    @Test
    public void testCreateCardTransfer_ShouldCreateCardTransfer() {

        when(cardTransferRepository.findByCardNumber(2203440098981212L)).thenReturn(Optional.empty());
        when(cardTransferRepository.save(any(CardTransferEntity.class))).thenReturn(cardTransferEntity);

        CardTransferDTO result = cardTransferService.createCardTransfer(cardTransferDTO);

        assertNotNull(result);
        assertEquals(2203440098981212L, result.getCardNumber());
    }

    @Test
    public void testCreateCardTransfer_ShouldThrowUniqueFieldEmptyException() {

        cardTransferDTO.setCardNumber(null);

        assertThrows(UniqueFieldEmptyException.class,
                () -> cardTransferService.createCardTransfer(cardTransferDTO));
    }

    @Test
    public void testCreateCardTransfer_ShouldThrowEntityAlreadyExistsException() {

        when(cardTransferRepository.findByCardNumber(2203440098981212L)).thenReturn(Optional.of(cardTransferEntity));

        assertThrows(EntityAlreadyExistsException.class,
                () -> cardTransferService.createCardTransfer(cardTransferDTO));
    }

    @Test
    public void testCreateCardTransfer_ShouldThrowRequiredFieldEmptyException() {

        cardTransferDTO.setAmount(null);

        assertThrows(RequiredFieldEmptyException.class,
                () -> cardTransferService.createCardTransfer(cardTransferDTO));
    }

    @Test
    public void testUpdateCardTransfer_ShouldUpdateCardTransfer() {

        when(cardTransferRepository.findById(1L)).thenReturn(Optional.of(cardTransferEntity));
        when(cardTransferRepository.findByCardNumber(2203440098981212L)).thenReturn(Optional.empty());
        when(cardTransferRepository.save(any(CardTransferEntity.class))).thenReturn(cardTransferEntity);

        CardTransferDTO result = cardTransferService.updateCardTransfer(cardTransferDTO, 1L);

        assertNotNull(result);
        assertEquals(2203440098981212L, result.getCardNumber());
    }

    @Test
    public void testUpdateCardTransfer_ShouldThrowEntityNotFoundException() {

        when(cardTransferRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> cardTransferService.updateCardTransfer(cardTransferDTO, 1L));
    }

    @Test
    public void testUpdateCardTransfer_ShouldThrowAlreadyExistsException() {

        CardTransferEntity anotherEntity = CardTransferEntity.builder()
                .id(2L)
                .cardNumber(2203440098981212L)
                .build();

        when(cardTransferRepository.findById(1L)).thenReturn(Optional.of(cardTransferEntity));
        when(cardTransferRepository.findByCardNumber(2203440098981212L)).thenReturn(Optional.of(anotherEntity));

        assertThrows(EntityAlreadyExistsException.class, () -> cardTransferService.updateCardTransfer(cardTransferDTO, 1L));
    }

    @Test
    public void testDeleteCardTransfer_ShouldDeleteCardTransfer() {

        when(cardTransferRepository.findById(1L)).thenReturn(Optional.of(cardTransferEntity));

        CardTransferDTO result = cardTransferService.deleteCardTransfer(1L);

        assertNotNull(result);
        assertEquals(2203440098981212L, result.getCardNumber());

        verify(cardTransferRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteCardTransfer_ShouldThrowEntityNotFoundException() {

        when(cardTransferRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> cardTransferService.deleteCardTransfer(1L));
    }
}

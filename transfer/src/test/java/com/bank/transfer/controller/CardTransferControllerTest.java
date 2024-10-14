package com.bank.transfer.controller;

import com.bank.transfer.dto.CardTransferDTO;
import com.bank.transfer.exception.EntityAlreadyExistsException;
import com.bank.transfer.exception.EntityNotFoundException;
import com.bank.transfer.exception.RequiredFieldEmptyException;
import com.bank.transfer.exception.UniqueFieldEmptyException;
import com.bank.transfer.service.CardTransferServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

/**
 * Класс CardTransferControllerTest содержит тесты для контроллера CardTransferController.
 *
 * @since 1.0
 */
@WebMvcTest(CardTransferController.class)
public class CardTransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardTransferServiceImpl cardTransferService;

    @Autowired
    private ObjectMapper objectMapper;

    private CardTransferDTO cardTransferDTO1;

    private CardTransferDTO cardTransferDTO2;

    private CardTransferDTO conflictDTO;

    private CardTransferDTO emptyUniqueFieldDTO;

    private CardTransferDTO emptyRequiredFieldDTO;

    @BeforeEach
    void setUp() {

        cardTransferDTO1 = CardTransferDTO.builder()
                .id(1L)
                .cardNumber(2200330044005500L)
                .amount(BigDecimal.valueOf(1000.00))
                .purpose("Test1")
                .accountDetailsId(100L)
                .build();

        cardTransferDTO2 = CardTransferDTO.builder()
                .id(2L)
                .cardNumber(3300440055006600L)
                .amount(BigDecimal.valueOf(10000.00))
                .purpose("Test2")
                .accountDetailsId(200L)
                .build();

        conflictDTO = CardTransferDTO.builder()
                .id(3L)
                .cardNumber(2200330044005500L)
                .amount(BigDecimal.valueOf(1000.00))
                .purpose("Test3")
                .accountDetailsId(300L)
                .build();

        emptyUniqueFieldDTO = CardTransferDTO.builder()
                .id(4L)
                .cardNumber(null)
                .amount(BigDecimal.valueOf(2000.00))
                .purpose("Test4")
                .accountDetailsId(400L)
                .build();

        emptyRequiredFieldDTO = CardTransferDTO.builder()
                .id(5L)
                .cardNumber(4400550066007700L)
                .amount(null)
                .purpose("Test5")
                .accountDetailsId(500L)
                .build();
    }

    /**
     * Тест проверяет получение списка переводов средств.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testGetCardTransferList() throws Exception {

        List<CardTransferDTO> cardTransferList = List.of(cardTransferDTO1, cardTransferDTO2);

        when(cardTransferService.getAllCardTransfers()).thenReturn(cardTransferList);

        mockMvc.perform(get("/card-transfer")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].cardNumber").value(2200330044005500L))
                .andExpect(jsonPath("$[0].amount").value(1000.00))
                .andExpect(jsonPath("$[0].purpose").value("Test1"))
                .andExpect(jsonPath("$[0].accountDetailsId").value(100L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].cardNumber").value(3300440055006600L))
                .andExpect(jsonPath("$[1].amount").value(10000.00))
                .andExpect(jsonPath("$[1].purpose").value("Test2"))
                .andExpect(jsonPath("$[1].accountDetailsId").value(200L));

        verify(cardTransferService, times(1)).getAllCardTransfers();
    }

    /**
     * Тест проверяет получение перевода средств по ID.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testGetCardTransferById() throws Exception {

        when(cardTransferService.getCardTransferById(1L)).thenReturn(cardTransferDTO1);

        mockMvc.perform(get("/card-transfer/{id}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cardNumber").value(2200330044005500L))
                .andExpect(jsonPath("$.amount").value(1000.00))
                .andExpect(jsonPath("$.purpose").value("Test1"))
                .andExpect(jsonPath("$.accountDetailsId").value(100L));

        verify(cardTransferService, times(1)).getCardTransferById(1L);
    }

    /**
     * Тест проверяет получение перевода средств по ID, когда объект не найден.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testGetCardTransferByIdNotFound() throws Exception {

        when(cardTransferService.getCardTransferById(22L))
                .thenThrow(
                        new EntityNotFoundException(
                                "An object with this ID was not found",
                                "CardTransferControllerTest"));

        mockMvc.perform(get("/card-transfer/{id}", 22L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("An object with this ID was not found"));
    }

    /**
     * Тест проверяет создание нового перевода средств.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testCreateCardTransfer() throws Exception {

        when(cardTransferService.createCardTransfer(any(CardTransferDTO.class))).thenReturn(cardTransferDTO1);

        mockMvc.perform(post("/card-transfer")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardTransferDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cardNumber").value(2200330044005500L))
                .andExpect(jsonPath("$.amount").value(1000.00))
                .andExpect(jsonPath("$.purpose").value("Test1"))
                .andExpect(jsonPath("$.accountDetailsId").value(100L));

        verify(cardTransferService, times(1)).createCardTransfer(any(CardTransferDTO.class));
    }

    /**
     * Тест проверяет создание перевода средств с дублирующимся номером карты.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testCreateCardTransferWithDuplicateCardNumber() throws Exception {

        when(cardTransferService.createCardTransfer(any(CardTransferDTO.class)))
                .thenThrow(
                        new EntityAlreadyExistsException(
                                "An object with the same card number already exists",
                                "CardTransferControllerTest"));

        mockMvc.perform(post("/card-transfer")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(conflictDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$")
                        .value("An object with the same card number already exists"));
    }

    /**
     * Тест проверяет создание перевода средств с пустым уникальным полем.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testCreateCardTransferWithEmptyUniqueField() throws Exception {

        when(cardTransferService.createCardTransfer(any(CardTransferDTO.class)))
                .thenThrow(
                        new UniqueFieldEmptyException(
                                "An empty unique field for CardTransferEntity",
                                "CardTransferControllerTest"));

        mockMvc.perform(post("/card-transfer")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyUniqueFieldDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("An empty unique field for CardTransferEntity"));
    }

    /**
     * Тест проверяет создание перевода средств с пустым обязательным полем.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testCreateCardTransferWithEmptyRequiredField() throws Exception {

        when(cardTransferService.createCardTransfer(any(CardTransferDTO.class)))
                .thenThrow(
                        new RequiredFieldEmptyException(
                                "An empty required field",
                                "CardTransferControllerTest"));

        mockMvc.perform(post("/card-transfer")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyRequiredFieldDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("An empty required field"));
    }

    /**
     * Тест проверяет обновление существующего перевода средств.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testUpdateCardTransfer() throws Exception {

        when(cardTransferService.updateCardTransfer(any(CardTransferDTO.class), eq(2L)))
                .thenReturn(cardTransferDTO2);

        mockMvc.perform(put("/card-transfer/{id}", 2L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardTransferDTO2)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.cardNumber").value(3300440055006600L))
                .andExpect(jsonPath("$.amount").value(10000.00))
                .andExpect(jsonPath("$.purpose").value("Test2"))
                .andExpect(jsonPath("$.accountDetailsId").value(200L));

        verify(cardTransferService, times(1))
                .updateCardTransfer(any(CardTransferDTO.class), eq(2L));
    }

    /**
     * Тест проверяет обновление перевода средств, когда объект не найден.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testUpdateCardTransferEntityNotFound() throws Exception {

        when(cardTransferService.updateCardTransfer(any(CardTransferDTO.class), eq(22L)))
                .thenThrow(
                        new EntityNotFoundException(
                                "An object with this ID was not found for editing",
                                "CardTransferControllerTest"));

        mockMvc.perform(put("/card-transfer/{id}", 22L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardTransferDTO2)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("An object with this ID was not found for editing"));
    }

    /**
     * Тест проверяет обновление перевода средств, когда объект с таким номером счета уже существует под другим ID.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testUpdateCardTransferEntityAlreadyExists() throws Exception {

        when(cardTransferService.updateCardTransfer(any(CardTransferDTO.class), eq(2L)))
                .thenThrow(
                        new EntityAlreadyExistsException(
                                "An object with the same card number already exists with a different ID",
                                "CardTransferControllerTest"));

        mockMvc.perform(put("/card-transfer/{id}", 2L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(conflictDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$")
                        .value("An object with the same card number already exists with a different ID"));
    }

    /**
     * Тест проверяет удаление перевода средств.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testDeleteCardTransfer() throws Exception {

        when(cardTransferService.deleteCardTransfer(2L)).thenReturn(cardTransferDTO2);

        mockMvc.perform(delete("/card-transfer/{id}", 2L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.cardNumber").value(3300440055006600L))
                .andExpect(jsonPath("$.amount").value(10000.00))
                .andExpect(jsonPath("$.purpose").value("Test2"))
                .andExpect(jsonPath("$.accountDetailsId").value(200L));

        verify(cardTransferService, times(1)).deleteCardTransfer(2L);
    }

    /**
     * Тест проверяет удаление перевода средств, когда объект не найден.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testDeleteCardTransferEntityNotFound() throws Exception {

        when(cardTransferService.deleteCardTransfer(2L))
                .thenThrow(
                        new EntityNotFoundException(
                                "An object with this ID was not found for deletion",
                                "CardTransferControllerTest"));

        mockMvc.perform(delete("/card-transfer/{id}", 2L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$")
                        .value("An object with this ID was not found for deletion"));
    }
}

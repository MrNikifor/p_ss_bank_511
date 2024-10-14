package com.bank.transfer.controller;

import com.bank.transfer.dto.PhoneTransferDTO;
import com.bank.transfer.exception.EntityNotFoundException;
import com.bank.transfer.exception.RequiredFieldEmptyException;
import com.bank.transfer.service.PhoneTransferServiceImpl;
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
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Класс PhoneTransferControllerTest содержит тесты для контроллера PhoneTransferController.
 *
 * @since 1.0
 */
@WebMvcTest(PhoneTransferController.class)
public class PhoneTransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneTransferServiceImpl phoneTransferService;

    @Autowired
    private ObjectMapper objectMapper;

    private PhoneTransferDTO phoneTransferDTO;

    private PhoneTransferDTO phoneTransferDTO2;

    private PhoneTransferDTO emptyRequiredFieldDTO;

    @BeforeEach
    void setUp() {

        phoneTransferDTO = PhoneTransferDTO.builder()
                .id(1L)
                .phoneNumber(89109090909L)
                .amount(BigDecimal.valueOf(2100.00))
                .purpose("test1")
                .accountDetailsId(100L)
                .build();

        phoneTransferDTO2 = PhoneTransferDTO.builder()
                .id(2L)
                .phoneNumber(89208080808L)
                .amount(BigDecimal.valueOf(22000.00))
                .purpose("test2")
                .accountDetailsId(200L)
                .build();

        emptyRequiredFieldDTO = PhoneTransferDTO.builder()
                .id(3L)
                .phoneNumber(null)
                .amount(BigDecimal.valueOf(200.00))
                .purpose("test3")
                .accountDetailsId(300L)
                .build();

    }

    /**
     * Тест проверяет получение списка переводов средств.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testGetPhoneTransferList() throws Exception {

        List<PhoneTransferDTO> phoneTransferList = List.of(phoneTransferDTO, phoneTransferDTO2);

        when(phoneTransferService.getAllPhoneTransfers()).thenReturn(phoneTransferList);

        mockMvc.perform(get("/phone-transfer")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].phoneNumber").value(89109090909L))
                .andExpect(jsonPath("$[0].amount").value(2100.00))
                .andExpect(jsonPath("$[0].purpose").value("test1"))
                .andExpect(jsonPath("$[0].accountDetailsId").value(100L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].phoneNumber").value(89208080808L))
                .andExpect(jsonPath("$[1].amount").value(22000.00))
                .andExpect(jsonPath("$[1].purpose").value("test2"))
                .andExpect(jsonPath("$[1].accountDetailsId").value(200L));

        verify(phoneTransferService, times(1)).getAllPhoneTransfers();
    }

    /**
     * Тест проверяет получение перевода средств по ID.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testGetPhoneTransferById() throws Exception {

        when(phoneTransferService.getPhoneTransferById(1L)).thenReturn(phoneTransferDTO);

        mockMvc.perform(get("/phone-transfer/{id}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.phoneNumber").value(89109090909L))
                .andExpect(jsonPath("$.amount").value(2100.00))
                .andExpect(jsonPath("$.purpose").value("test1"))
                .andExpect(jsonPath("$.accountDetailsId").value(100L));

        verify(phoneTransferService, times(1)).getPhoneTransferById(1L);
    }

    /**
     * Тест проверяет получение перевода средств по ID, когда объект не найден.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testGetPhoneTransferByIdNotFound() throws Exception {

        when(phoneTransferService.getPhoneTransferById(22L))
                .thenThrow(
                        new EntityNotFoundException(
                                "An object with this ID was not found",
                                "PhoneTransferControllerTest"));

        mockMvc.perform(get("/phone-transfer/{id}", 22L)
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
    public void testCreatePhoneTransfer() throws Exception {

        when(phoneTransferService.createPhoneTransfer(any(PhoneTransferDTO.class))).thenReturn(phoneTransferDTO);

        mockMvc.perform(post("/phone-transfer")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(phoneTransferDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.phoneNumber").value(89109090909L))
                .andExpect(jsonPath("$.amount").value(2100.00))
                .andExpect(jsonPath("$.purpose").value("test1"))
                .andExpect(jsonPath("$.accountDetailsId").value(100L));

        verify(phoneTransferService, times(1)).createPhoneTransfer(any(PhoneTransferDTO.class));
    }

    /**
     * Тест проверяет создание перевода средств с пустым обязательным полем.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testCreatePhoneTransferWithEmptyRequiredField() throws Exception {

        when(phoneTransferService.createPhoneTransfer(any(PhoneTransferDTO.class)))
                .thenThrow(
                        new RequiredFieldEmptyException(
                                "An empty required field has been passed",
                                "PhoneTransferControllerTest"));

        mockMvc.perform(post("/phone-transfer")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyRequiredFieldDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("An empty required field has been passed"));
    }

    /**
     * Тест проверяет обновление существующего перевода средств.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testUpdatePhoneTransfer() throws Exception {

        when(phoneTransferService.updatePhoneTransfer(any(PhoneTransferDTO.class), eq(2L)))
                .thenReturn(phoneTransferDTO2);

        mockMvc.perform(put("/phone-transfer/{id}", 2L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(phoneTransferDTO2)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.phoneNumber").value(phoneTransferDTO2.getPhoneNumber()))
                .andExpect(jsonPath("$.amount").value(phoneTransferDTO2.getAmount()))
                .andExpect(jsonPath("$.purpose").value(phoneTransferDTO2.getPurpose()))
                .andExpect(jsonPath("$.accountDetailsId").value(phoneTransferDTO2.getAccountDetailsId()));

        verify(phoneTransferService, times(1))
                .updatePhoneTransfer(any(PhoneTransferDTO.class), eq(2L));
    }

    /**
     * Тест проверяет обновление перевода средств, когда объект не найден.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testUpdatePhoneTransferEntityNotFound() throws Exception {

        when(phoneTransferService.updatePhoneTransfer(any(PhoneTransferDTO.class), eq(22L)))
                .thenThrow(
                        new EntityNotFoundException(
                                "An object with this ID was not found for editing",
                                "PhoneTransferControllerTest"));

        mockMvc.perform(put("/phone-transfer/{id}", 22L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(phoneTransferDTO2)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$")
                        .value("An object with this ID was not found for editing"));
    }

    /**
     * Тест проверяет удаление перевода средств.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testDeletePhoneTransfer() throws Exception {

        when(phoneTransferService.deletePhoneTransfer(2L)).thenReturn(phoneTransferDTO2);

        mockMvc.perform(delete("/phone-transfer/{id}", 2L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.phoneNumber").value(phoneTransferDTO2.getPhoneNumber()))
                .andExpect(jsonPath("$.amount").value(phoneTransferDTO2.getAmount()))
                .andExpect(jsonPath("$.purpose").value(phoneTransferDTO2.getPurpose()))
                .andExpect(jsonPath("$.accountDetailsId").value(phoneTransferDTO2.getAccountDetailsId()));

        verify(phoneTransferService, times(1)).deletePhoneTransfer(2L);
    }

    /**
     * Тест проверяет удаление перевода средств, когда объект не найден.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testDeletePhoneTransferEntityNotFound() throws Exception {

        when(phoneTransferService.deletePhoneTransfer(2L))
                .thenThrow(
                        new EntityNotFoundException(
                                "An object with this ID was not found for deletion",
                                "PhoneTransferControllerTest"));

        mockMvc.perform(delete("/phone-transfer/{id}", 2L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$")
                        .value("An object with this ID was not found for deletion"));
    }
}

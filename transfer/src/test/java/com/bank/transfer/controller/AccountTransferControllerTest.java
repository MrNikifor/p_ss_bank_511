package com.bank.transfer.controller;

import com.bank.transfer.dto.AccountTransferDTO;
import com.bank.transfer.exception.EntityAlreadyExistsException;
import com.bank.transfer.exception.EntityNotFoundException;
import com.bank.transfer.exception.RequiredFieldEmptyException;
import com.bank.transfer.exception.UniqueFieldEmptyException;
import com.bank.transfer.service.AccountTransferServiceImpl;
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
 * Класс AccountTransferControllerTest содержит тесты для контроллера AccountTransferController.
 *
 * @since 1.0
 */
@WebMvcTest(AccountTransferController.class)
public class AccountTransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountTransferServiceImpl accountTransferService;

    @Autowired
    private ObjectMapper objectMapper;

    private AccountTransferDTO accountTransferDTO1;

    private AccountTransferDTO accountTransferDTO2;

    private AccountTransferDTO conflictDTO;

    private AccountTransferDTO emptyUniqueFieldDTO;

    private AccountTransferDTO emptyRequiredFieldDTO;

    @BeforeEach
    void setUp() {

        accountTransferDTO1 = AccountTransferDTO.builder()
                .id(1L)
                .accountNumber(12341L)
                .amount(BigDecimal.valueOf(112.00))
                .purpose("Test1")
                .accountDetailsId(111L)
                .build();

        accountTransferDTO2 = AccountTransferDTO.builder()
                .id(2L)
                .accountNumber(12345L)
                .amount(BigDecimal.valueOf(1125.00))
                .purpose("Test2")
                .accountDetailsId(222L)
                .build();

        conflictDTO = AccountTransferDTO.builder()
                .id(3L)
                .accountNumber(12341L)
                .amount(BigDecimal.valueOf(1122.00))
                .purpose("Test4")
                .accountDetailsId(100L)
                .build();

        emptyRequiredFieldDTO = AccountTransferDTO.builder()
                .id(4L)
                .accountNumber(12346L)
                .amount(null)
                .purpose("Test5")
                .accountDetailsId(200L)
                .build();

        emptyUniqueFieldDTO = AccountTransferDTO.builder()
                .id(5L)
                .accountNumber(null)
                .amount(BigDecimal.valueOf(123.00))
                .purpose("Test6")
                .accountDetailsId(300L)
                .build();
    }

    /**
     * Тест проверяет получение списка переводов средств.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testGetAccountTransferList() throws Exception {

        List<AccountTransferDTO> accountTransferList = List.of(accountTransferDTO1, accountTransferDTO2);

        when(accountTransferService.getAllAccountTransfers()).thenReturn(accountTransferList);

        mockMvc.perform(get("/account-transfer")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].accountNumber").value(12341L))
                .andExpect(jsonPath("$[0].amount").value(BigDecimal.valueOf(112.00)))
                .andExpect(jsonPath("$[0].purpose").value("Test1"))
                .andExpect(jsonPath("$[0].accountDetailsId").value(111L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].accountNumber").value(12345L))
                .andExpect(jsonPath("$[1].amount").value(BigDecimal.valueOf(1125.00)))
                .andExpect(jsonPath("$[1].purpose").value("Test2"))
                .andExpect(jsonPath("$[1].accountDetailsId").value(222L));

        verify(accountTransferService, times(1)).getAllAccountTransfers();
    }

    /**
     * Тест проверяет получение перевода средств по ID.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testGetAccountTransferById() throws Exception {

        when(accountTransferService.getAccountTransferById(1L)).thenReturn(accountTransferDTO1);

        mockMvc.perform(get("/account-transfer/{id}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountNumber").value(12341L))
                .andExpect(jsonPath("$.amount").value(BigDecimal.valueOf(112.00)))
                .andExpect(jsonPath("$.purpose").value("Test1"))
                .andExpect(jsonPath("$.accountDetailsId").value(111L));

        verify(accountTransferService, times(1)).getAccountTransferById(1L);
    }

    /**
     * Тест проверяет создание нового перевода средств.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testCreateAccountTransfer() throws Exception {

        when(accountTransferService.createAccountTransfer(any(AccountTransferDTO.class)))
                .thenReturn(accountTransferDTO1);

        mockMvc.perform(post("/account-transfer")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountTransferDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountNumber").value(12341L))
                .andExpect(jsonPath("$.amount").value(BigDecimal.valueOf(112.00)))
                .andExpect(jsonPath("$.purpose").value("Test1"))
                .andExpect(jsonPath("$.accountDetailsId").value(111L));

        verify(accountTransferService, times(1))
                .createAccountTransfer(any(AccountTransferDTO.class));
    }

    /**
     * Тест проверяет обновление существующего перевода средств.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testUpdateAccountTransfer() throws Exception {

        when(accountTransferService.updateAccountTransfer(any(AccountTransferDTO.class), eq(1L)))
                .thenReturn(accountTransferDTO1);

        mockMvc.perform(put("/account-transfer/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountTransferDTO1)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountNumber").value(12341L))
                .andExpect(jsonPath("$.amount").value(BigDecimal.valueOf(112.00)))
                .andExpect(jsonPath("$.purpose").value("Test1"))
                .andExpect(jsonPath("$.accountDetailsId").value(111L));

        verify(accountTransferService, times(1))
                .updateAccountTransfer(any(AccountTransferDTO.class), eq(1L));
    }

    /**
     * Тест проверяет удаление перевода средств.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testDeleteAccountTransfer() throws Exception {

        when(accountTransferService.deleteAccountTransfer(1L)).thenReturn(accountTransferDTO1);

        mockMvc.perform(delete("/account-transfer/{id}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountNumber").value(12341L))
                .andExpect(jsonPath("$.amount").value(BigDecimal.valueOf(112.00)))
                .andExpect(jsonPath("$.purpose").value("Test1"))
                .andExpect(jsonPath("$.accountDetailsId").value(111L));

        verify(accountTransferService, times(1)).deleteAccountTransfer(1L);
    }

    /**
     * Тест проверяет получение перевода средств по ID, когда объект не найден.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testGetAccountTransferByIdNotFound() throws Exception {

        when(accountTransferService.getAccountTransferById(88L))
                .thenThrow(
                        new EntityNotFoundException(
                                "An object with this ID was not found",
                                "AccountTransferControllerTest.testGetAccountTransferByIdNotFound"));

        mockMvc.perform(get("/account-transfer/{id}", 88L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("An object with this ID was not found"));
    }

    /**
     * Тест проверяет создание перевода средств с дублирующимся номером счета.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testCreateAccountTransferConflictWithDuplicateAccountNumber() throws Exception {

        when(accountTransferService.createAccountTransfer(any(AccountTransferDTO.class)))
                .thenThrow(
                        new EntityAlreadyExistsException(
                                "An object with the same account number already exists",
                                "AccountTransferControllerTest"));

        mockMvc.perform(post("/account-transfer")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(conflictDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$")
                        .value("An object with the same account number already exists"));
    }

    /**
     * Тест проверяет создание перевода средств с пустым уникальным полем.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testCreateAccountTransferWithEmptyUniqueField() throws Exception {

        when(accountTransferService.createAccountTransfer(any(AccountTransferDTO.class)))
                .thenThrow(
                        new UniqueFieldEmptyException(
                                "An empty unique field for AccountTransferEntity",
                                "AccountTransferControllerTest"));

        mockMvc.perform(post("/account-transfer")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyUniqueFieldDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$")
                        .value("An empty unique field for AccountTransferEntity"));
    }

    /**
     * Тест проверяет создание перевода средств с пустым обязательным полем.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testCreateAccountTransferWithEmptyRequiredField() throws Exception {

        when(accountTransferService.createAccountTransfer(any(AccountTransferDTO.class)))
                .thenThrow(
                        new RequiredFieldEmptyException(
                                "An empty required field",
                                "AccountTransferControllerTest"));

        mockMvc.perform(post("/account-transfer")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyRequiredFieldDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("An empty required field"));
    }

    /**
     * Тест проверяет обновление перевода средств, когда объект не найден.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testUpdateAccountTransferEntityNotFound() throws Exception {

        when(accountTransferService.updateAccountTransfer(any(AccountTransferDTO.class), eq(99L)))
                .thenThrow(
                        new EntityNotFoundException(
                                "An object with this ID was not found for editing",
                                "AccountTransferControllerTest"));

        mockMvc.perform(put("/account-transfer/{id}", 99L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountTransferDTO1)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$")
                        .value("An object with this ID was not found for editing"));
    }

    /**
     * Тест проверяет обновление перевода средств, когда объект с таким номером счета уже существует под другим ID.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testUpdateAccountTransferEntityAlreadyExists() throws Exception {

        when(accountTransferService.updateAccountTransfer(any(AccountTransferDTO.class), eq(4L)))
                .thenThrow(
                        new EntityAlreadyExistsException(
                                "An object with the same account number already exists with a different ID",
                                "AccountTransferControllerTest"));

        mockMvc.perform(put("/account-transfer/{id}", 4L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(conflictDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$")
                        .value("An object with the same account number already exists with a different ID"));
    }

    /**
     * Тест проверяет удаление перевода средств, когда объект не найден.
     *
     * @throws Exception если возникает ошибка при выполнении теста.
     */
    @Test
    public void testDeleteAccountTransferEntityNotFound() throws Exception {

        when(accountTransferService.deleteAccountTransfer(78L))
                .thenThrow(
                        new EntityNotFoundException(
                                "An object with this ID was not found for deletion",
                                "AccountTransferControllerTest"));

        mockMvc.perform(delete("/account-transfer/{id}", 78L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$")
                        .value("An object with this ID was not found for deletion"));
    }
}

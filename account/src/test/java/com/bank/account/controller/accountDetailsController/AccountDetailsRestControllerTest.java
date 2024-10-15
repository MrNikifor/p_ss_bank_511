package com.bank.account.controller.accountDetailsController;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bank.account.dto.AccountDetailsDTO;
import com.bank.account.entity.AccountDetails;
import com.bank.account.mapper.AccountDetailsMapper;
import com.bank.account.service.AccountDetailsService;
import com.bank.account.validator.ValidatorDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.List;


@WebMvcTest(AccountDetailsRestController.class)
class AccountDetailsRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AccountDetailsMapper accountDetailsMapper;
    @MockBean
    private AccountDetailsService accountDetailsService;
    @MockBean
    private ValidatorDTO validator;
    private AccountDetails accountDetails;
    private AccountDetailsDTO accountDetailsDTO;

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

        accountDetailsDTO = new AccountDetailsDTO();
        accountDetailsDTO.setId(1L);
        accountDetailsDTO.setPassportId(1L);
        accountDetailsDTO.setAccountNumber(1L);
        accountDetailsDTO.setBankDetailsId(1L);
        accountDetailsDTO.setMoney(BigDecimal.valueOf(1234567.00));
        accountDetailsDTO.setNegativeBalance(false);
        accountDetailsDTO.setProfileId(1L);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void getAllAccountDetails() throws Exception {
        when(accountDetailsService.getAllAccountDetails()).thenReturn(List.of(accountDetails));

        mockMvc.perform(get("/rest/account_details")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(accountDetails.getId()))
                .andExpect(jsonPath("$[0].passportId").value(accountDetails.getPassportId()))
                .andExpect(jsonPath("$[0].accountNumber").value(accountDetails.getAccountNumber()))
                .andExpect(jsonPath("$[0].bankDetailsId").value(accountDetails.getBankDetailsId()))
                .andExpect(jsonPath("$[0].money").value(accountDetails.getMoney().toString()))
                .andExpect(jsonPath("$[0].negativeBalance").value(accountDetails.isNegativeBalance()))
                .andExpect(jsonPath("$[0].profileId").value(accountDetails.getProfileId()));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void getAccountDetailsById() throws Exception {
        when(accountDetailsService.getAccountDetails(1L)).thenReturn(accountDetails);

        mockMvc.perform(get("/rest/account_details/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountDetails.getId()))
                .andExpect(jsonPath("$.passportId").value(accountDetails.getPassportId()))
                .andExpect(jsonPath("$.accountNumber").value(accountDetails.getAccountNumber()))
                .andExpect(jsonPath("$.bankDetailsId").value(accountDetails.getBankDetailsId()))
                .andExpect(jsonPath("$.money").value(accountDetails.getMoney().toString()))
                .andExpect(jsonPath("$.negativeBalance").value(accountDetails.isNegativeBalance()))
                .andExpect(jsonPath("$.profileId").value(accountDetails.getProfileId()));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void saveAccountDetails() throws Exception {
        when(accountDetailsMapper.toEntity(accountDetailsDTO)).thenReturn(accountDetails);
        when(accountDetailsService.saveAccountDetails(accountDetails)).thenReturn(accountDetails);

        mockMvc.perform(post("/rest/account_details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDetailsDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(accountDetails.getId()));
        verify(accountDetailsService, times(1)).saveAccountDetails(accountDetails);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void saveAccountDetailsWithCatchValidationException() throws Exception {
        accountDetailsDTO.setAccountNumber(null);
        when(validator.validate(accountDetailsDTO))
                .thenThrow(new ValidationException("Данные не валидны, ошибки валидации: Номер аккаунта не может быть пустым"));

        mockMvc.perform(post("/rest/account_details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDetailsDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void updateAccountDetails() throws Exception {
        when(accountDetailsMapper.toEntity(accountDetailsDTO)).thenReturn(accountDetails);
        when(accountDetailsService.updateAccountDetails(accountDetails)).thenReturn(accountDetails);

        mockMvc.perform(put("/rest/account_details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDetailsDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountDetails.getId()));
        verify(accountDetailsService, times(1)).updateAccountDetails(accountDetails);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void updateAccountDetailsWithCatchValidationException() throws Exception {
        accountDetailsDTO.setAccountNumber(null);
        when(validator.validate(accountDetailsDTO))
                .thenThrow(new ValidationException("Данные не валидны, ошибки валидации: Номер аккаунта не может быть пустым"));

        mockMvc.perform(put("/rest/account_details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDetailsDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void deleteAccountDetails() throws Exception {
        mockMvc.perform(delete("/rest/account_details/{id}", 1L))
                .andExpect(status().isOk());
        verify(accountDetailsService, times(1))
                .deleteAccountDetails(accountDetailsService.getAccountDetails(1L));
    }
}
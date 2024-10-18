package com.bank.account.controller;

import com.bank.account.dto.AccountDetailsDTO;
import com.bank.account.service.AccountDetailsService;
import com.bank.account.validator.ValidatorDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest")
public class AccountDetailsRestController {
    private final AccountDetailsService accountDetailsService;
    private final ValidatorDTO validatorDTO;

    @Tag(name = "Получение всех счетов", description = "Получение всех имеющихся счетов на данный момент")
    @GetMapping("/account_details")
    public List<AccountDetailsDTO> getAllAccountDetails() {
        return accountDetailsService.getAllAccountDetails();
    }

    @Tag(name = "Получение одного счёта", description = "Получение счёта по его ID(ID указывается в заголовке")
    @GetMapping("/account_details/{id}")
    public AccountDetailsDTO getAccountDetailsById(@PathVariable Long id) {
        return accountDetailsService.getAccountDetails(id);
    }

    @Tag(name = "Создание счёта", description = "Создание одного счёта(ID в теле запроса не указывается)")
    @PostMapping("/account_details")
    public ResponseEntity saveAccountDetails(@RequestBody AccountDetailsDTO accountDetailsDTO) {
        try {
            validatorDTO.validate(accountDetailsDTO);
            accountDetailsService.saveAccountDetails(accountDetailsDTO);
            return new ResponseEntity(accountDetailsDTO, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity("Ошибка при создании счёта: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Tag(name = "Изменение счёта", description = "Изменение счёта по ID(ID в теле запроса указывать обязательно)")
    @PutMapping("/account_details")
    public ResponseEntity updateAccountDetails(@RequestBody AccountDetailsDTO accountDetailsDTO) {
        try {
            validatorDTO.validate(accountDetailsDTO);
            accountDetailsService.updateAccountDetails(accountDetailsDTO);
            return new ResponseEntity<>(accountDetailsDTO, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>("Ошибка при обновлении счёта: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Tag(name = "Удаление счёта", description = "Удаление счёта по ID(ID в теле запроса указывать обязательно)")
    @DeleteMapping("/account_details/{id}")
    public void deleteAccountDetails(@PathVariable Long id) {
        accountDetailsService.deleteAccountDetails(accountDetailsService.getAccountDetails(id));
    }
}

package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.service.BankDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bank-details")
@Validated
@Slf4j
public class BankDetailsController {

    private final BankDetailsService bankDetailsService;

    @Autowired
    public BankDetailsController(BankDetailsService bankDetailsService) {
        this.bankDetailsService = bankDetailsService;
    }

    @Operation(summary = "Создать новые реквизиты банка")
    @ApiResponse(responseCode = "201", description = "Реквизиты банка успешно созданы")
    @PostMapping
    public ResponseEntity<BankDetailsDto> createBankDetails(@Valid @RequestBody BankDetailsDto bankDetailsDto) {

        log.info("Creating bank details with data: {}", bankDetailsDto);

        if (bankDetailsDto.getJointStockCompany() == null) {
            log.error("Joint stock company is null");
            throw new IllegalArgumentException("Joint stock company must not be null");
        }

        BankDetailsDto createdBankDetails = bankDetailsService.createBankDetails(bankDetailsDto);

        log.info("Bank details created successfully with ID: {}", createdBankDetails.getId());

        return new ResponseEntity<>(createdBankDetails, HttpStatus.CREATED);
    }

    @Operation(summary = "Получить реквизиты банка по ID")
    @ApiResponse(responseCode = "200", description = "Реквизиты банка успешно получены")
    @ApiResponse(responseCode = "404", description = "Реквизиты банка не найдены")
    @GetMapping("/{id}")
    public ResponseEntity<BankDetailsDto> getBankDetailsById(@PathVariable Long id) {

        log.info("Fetching bank details with ID: {}", id);

        BankDetailsDto bankDetails = bankDetailsService.getBankDetailsById(id);

        log.info("Bank details retrieved successfully: {}", bankDetails);

        return new ResponseEntity<>(bankDetails, HttpStatus.OK);
    }

    @Operation(summary = "Получить список всех реквизитов банков")
    @ApiResponse(responseCode = "200", description = "Список реквизитов банков успешно получен")
    @GetMapping
    public ResponseEntity<List<BankDetailsDto>> getAllBankDetails() {

        log.info("Fetching all bank details");

        List<BankDetailsDto> bankDetailsList = bankDetailsService.getAllBankDetails();

        log.info("Total bank details retrieved: {}", bankDetailsList.size());

        return new ResponseEntity<>(bankDetailsList, HttpStatus.OK);
    }

    @Operation(summary = "Обновить информацию о реквизитах банка")
    @ApiResponse(responseCode = "200", description = "Информация о реквизитах банка успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Реквизиты банка не найдены")
    @PutMapping("/{id}")
    public ResponseEntity<BankDetailsDto> updateBankDetails(@PathVariable Long id,
                                                            @Valid @RequestBody BankDetailsDto bankDetailsDTO) {
        log.info("Updating bank details with ID: {}", id);

        bankDetailsDTO.setId(id);
        BankDetailsDto updatedBankDetails = bankDetailsService.updateBankDetails(bankDetailsDTO);

        log.info("Bank details updated successfully with ID: {}", updatedBankDetails.getId());

        return new ResponseEntity<>(updatedBankDetails, HttpStatus.OK);
    }

    @Operation(summary = "Удалить реквизиты банка по ID")
    @ApiResponse(responseCode = "204", description = "Реквизиты банка успешно удалены")
    @ApiResponse(responseCode = "404", description = "Реквизиты банка не найдены")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankDetail(@PathVariable Long id) {

        log.info("Deleting bank details with ID: {}", id);

        bankDetailsService.deleteBankDetails(id);

        log.info("Bank details deleted successfully with ID: {}", id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.ATMDTO;
import com.bank.publicinfo.service.ATMService;
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
@RequestMapping("/atms")
@Validated
@Slf4j
public class ATMController {

    private final ATMService atmService;

    @Autowired
    public ATMController(ATMService atmService) {
        this.atmService = atmService;
    }

    @Operation(summary = "Создать новый банкомат")
    @ApiResponse(responseCode = "201", description = "Банкомат успешно создан")
    @PostMapping
    public ResponseEntity<ATMDTO> createATM(@Valid @RequestBody ATMDTO atmDTO) {

        log.info("Creating ATM with details: {}", atmDTO);

        ATMDTO createdATM = atmService.createATM(atmDTO);

        log.info("ATM created successfully with ID: {}", createdATM.getId());

        return new ResponseEntity<>(createdATM, HttpStatus.CREATED);
    }

    @Operation(summary = "Получить банкомат по ID")
    @ApiResponse(responseCode = "200", description = "Информация о банкомате успешно получена")
    @ApiResponse(responseCode = "404", description = "Банкомат не найден")
    @GetMapping("/{id}")
    public ResponseEntity<ATMDTO> getATMById(@PathVariable Long id) {

        log.info("Fetching ATM with ID: {}", id);

        ATMDTO atm = atmService.getATMById(id);

        log.info("ATM retrieved successfully: {}", atm);

        return new ResponseEntity<>(atm, HttpStatus.OK);
    }

    @Operation(summary = "Получить список всех банкоматов")
    @ApiResponse(responseCode = "200", description = "Список банкоматов успешно получен")
    @GetMapping
    public ResponseEntity<List<ATMDTO>> getAllATMs() {

        log.info("Fetching all ATMs");

        List<ATMDTO> atms = atmService.getAllATMs();

        log.info("Total ATMs retrieved: {}", atms.size());

        return new ResponseEntity<>(atms, HttpStatus.OK);
    }

    @Operation(summary = "Обновить информацию о банкомате")
    @ApiResponse(responseCode = "204", description = "Информация о банкомате успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Банкомат не найден")
    @PutMapping("/{id}")
    public ResponseEntity<ATMDTO> updateATM(@PathVariable Long id, @Valid @RequestBody ATMDTO atmDTO) {

        log.info("Updating ATM with ID: {}", id);

        atmDTO.setId(id);
        ATMDTO updatedATM = atmService.updateATM(atmDTO);

        log.info("ATM updated successfully with ID: {}", updatedATM.getId());

        return new ResponseEntity<>(updatedATM, HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Удалить банкомат по ID")
    @ApiResponse(responseCode = "204", description = "Банкомат успешно удален")
    @ApiResponse(responseCode = "404", description = "Банкомат не найден")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteATM(@PathVariable Long id) {

        log.info("Deleting ATM with ID: {}", id);

        atmService.deleteATM(id);

        log.info("ATM deleted successfully with ID: {}", id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Проверить доступность банкомата")
    @ApiResponse(responseCode = "200", description = "Статус доступности банкомата успешно получен")
    @ApiResponse(responseCode = "404", description = "Банкомат не найден")
    @GetMapping("/{id}/availability")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable Long id) {

        log.info("Checking availability for ATM with ID: {}", id);

        boolean isAvailable = atmService.isATMAvailable(id);

        log.info("Availability status for ATM ID {}: {}", id, isAvailable);

        return new ResponseEntity<>(isAvailable, HttpStatus.OK);
    }
}


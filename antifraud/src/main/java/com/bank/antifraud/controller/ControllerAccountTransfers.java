package com.bank.antifraud.controller;


import com.bank.antifraud.dto.AccountTransfersDTO;
import com.bank.antifraud.service.SuspiciousAccountTransfersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/account_transfers")
public class ControllerAccountTransfers {

    private final SuspiciousAccountTransfersService sats;

    @GetMapping("/{id}")
    @Operation(summary = "Get account transfer by ID")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of account transfer")
    @ApiResponse(responseCode = "404", description = "Account transfer not found")
    public AccountTransfersDTO getSAT(@PathVariable Long id) {
        return sats.getAccountTransfer(id);
    }

    @GetMapping()
    @Operation(summary = "Get all account transfers")
    @ApiResponse(responseCode = "200", description = "List of all account transfers")
    public List<AccountTransfersDTO> getAllSAT() {
        return sats.getListSAccountTransfers();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete account transfer by ID")
    @ApiResponse(responseCode = "200", description = "Account transfer deleted successfully")
    @ApiResponse(responseCode = "404", description = "Account transfer not found")
    public void deleteSAT(@PathVariable Long id) {
        sats.deleteAccount(id);
    }

    @PostMapping()
    @Operation(summary = "Save new account transfer")
    @ApiResponse(responseCode = "201", description = "New account transfer saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public void saveSAT(@RequestBody AccountTransfersDTO transfersDTO) {
        sats.saveAccount(transfersDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing account transfer")
    @ApiResponse(responseCode = "200", description = "Account transfer updated successfully")
    @ApiResponse(responseCode = "404", description = "Account transfer not found")
    public void updateSAT(@RequestBody AccountTransfersDTO transfersDTO, @PathVariable Long id) {
        sats.updateAccount(transfersDTO, id);
    }
}

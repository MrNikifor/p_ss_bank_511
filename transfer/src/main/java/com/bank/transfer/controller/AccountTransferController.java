package com.bank.transfer.controller;

import com.bank.transfer.dto.AccountTransferDTO;
import com.bank.transfer.service.AccountTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

@Slf4j
@Tag(name = "Account Transfer", description = "API for managing account transfers")
@RestController
@RequestMapping("/account-transfer")
public class AccountTransferController {

    AccountTransferService accountTransferService;

    @Autowired
    public AccountTransferController(AccountTransferService accountTransferService) {
        this.accountTransferService = accountTransferService;
    }

    @Operation(summary = "Get a list of all Account transfers", description = "Return a list of all Account transfers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "No account transfers found"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")})
    @GetMapping
    public ResponseEntity<List<AccountTransferDTO>> getAccountTransferList() {

        log.info("Getting the account transfer list");

        return new ResponseEntity<>(accountTransferService.getAllAccountTransfers(), HttpStatus.OK);
    }

    @Operation(summary = "Getting the account transfer by ID",
            description = "Returns the account transfer by the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "The Account transfer was not found by the received ID"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")})
    @GetMapping("/{id}")
    public ResponseEntity<AccountTransferDTO> getAccountTransferById(@PathVariable Long id) {

        log.info("Getting the account transfer by id: {}", id);

        AccountTransferDTO accountTransferDTO = accountTransferService.getAccountTransferById(id);

        return new ResponseEntity<>(accountTransferDTO, HttpStatus.OK);
    }

    @Operation(summary = "Creating the account transfer", description = "Return the created Account transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account transfer created"),
            @ApiResponse(responseCode = "409", description = "Conflict of unique values during creation"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")})
    @PostMapping
    public ResponseEntity<AccountTransferDTO> createAccountTransfer(
            @RequestBody AccountTransferDTO accountTransferDTO) {

        log.info("Creating the account transfer : {}", accountTransferDTO);

        AccountTransferDTO createdAccountTransfer = accountTransferService.createAccountTransfer(accountTransferDTO);

        return new ResponseEntity<>(createdAccountTransfer, HttpStatus.CREATED);
    }

    @Operation(summary = "Updating the account transfer by ID", description = "Return the updated Account transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Account transfer updated"),
            @ApiResponse(responseCode = "409", description = "Conflict of unique values during the update"),
            @ApiResponse(responseCode = "404", description = "The account transfer was not found by the received ID"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")})
    @PutMapping("/{id}")
    public ResponseEntity<AccountTransferDTO> updateAccountTransfer(
            @PathVariable Long id, @RequestBody AccountTransferDTO accountTransferDTO) {

        log.info("Updating the account transfer by id: {} and by data: {}", id, accountTransferDTO);

        AccountTransferDTO updatedAccountTransfer =
                accountTransferService.updateAccountTransfer(accountTransferDTO, id);

        return new ResponseEntity<>(updatedAccountTransfer, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Deleting the account transfer by ID", description = "Return the deleted Account transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Account transfer deleted"),
            @ApiResponse(responseCode = "404", description = "The Account transfer was not found by the received ID"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")})
    @DeleteMapping("/{id}")
    public ResponseEntity<AccountTransferDTO> deleteAccountTransfer(@PathVariable Long id) {

        log.info("Deleting the account transfer by id: {}", id);

        AccountTransferDTO accountTransferDTO = accountTransferService.deleteAccountTransfer(id);

        return new ResponseEntity<>(accountTransferDTO, HttpStatus.ACCEPTED);
    }
}

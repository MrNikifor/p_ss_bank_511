package com.bank.transfer.controller;

import com.bank.transfer.dto.PhoneTransferDTO;
import com.bank.transfer.service.PhoneTransferService;
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
@Tag(name = "Phone Transfer", description = "API for managing phone transfers")
@RestController
@RequestMapping("/phone-transfer")
public class PhoneTransferController {

    private final PhoneTransferService phoneTransferService;

    @Autowired
    public PhoneTransferController(PhoneTransferService phoneTransferService) {
        this.phoneTransferService = phoneTransferService;
    }

    @Operation(summary = "Get a list of all Phone transfers", description = "Return a list of all Phone transfers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "No phone transfers found"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")})
    @GetMapping
    public ResponseEntity<List<PhoneTransferDTO>> getPhoneTransferList() {

        log.info("Getting the phone transfer list");

        return new ResponseEntity<>(phoneTransferService.getAllPhoneTransfers(), HttpStatus.OK);
    }

    @Operation(summary = "Getting the phone transfer by ID", description = "Return the phone transfer by the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "The phone transfer was not found by the received ID"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")})
    @GetMapping("/{id}")
    public ResponseEntity<PhoneTransferDTO> getPhoneTransferById(@PathVariable Long id) {

        log.info("Getting the phone transfer by id: {}", id);

        PhoneTransferDTO phoneTransferDTO = phoneTransferService.getPhoneTransferById(id);

        return new ResponseEntity<>(phoneTransferDTO, HttpStatus.OK);
    }

    @Operation(summary = "Creating the phone transfer", description = "Return the created phone transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Phone transfer created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")})
    @PostMapping
    public ResponseEntity<PhoneTransferDTO> createPhoneTransfer(@RequestBody PhoneTransferDTO phoneTransferDTO) {

        log.info("Creating the phone transfer : {}", phoneTransferDTO);

        PhoneTransferDTO createdPhoneTransfer = phoneTransferService.createPhoneTransfer(phoneTransferDTO);

        return new ResponseEntity<>(createdPhoneTransfer, HttpStatus.CREATED);
    }

    @Operation(summary = "Updating the phone transfer by ID", description = "Return the updated phone transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Phone transfer updated"),
            @ApiResponse(responseCode = "404", description = "The phone transfer was not found by the received ID"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")})
    @PutMapping("/{id}")
    public ResponseEntity<PhoneTransferDTO> updatePhoneTransfer(
            @PathVariable Long id, @RequestBody PhoneTransferDTO phoneTransferDTO) {

        log.info("Updating the phone transfer by id: {} and by data: {}", id, phoneTransferDTO);

        PhoneTransferDTO updatedPhoneTransfer = phoneTransferService.updatePhoneTransfer(phoneTransferDTO, id);

        return new ResponseEntity<>(updatedPhoneTransfer, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Deleting the phone transfer by ID", description = "Return the deleted phone transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Phone transfer deleted"),
            @ApiResponse(responseCode = "404", description = "The phone transfer was not found by the received ID"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")})
    @DeleteMapping("/{id}")
    public ResponseEntity<PhoneTransferDTO> deletePhoneTransfer(@PathVariable Long id) {

        log.info("Deleting the phone transfer by id: {}", id);

        PhoneTransferDTO phoneTransferDTO = phoneTransferService.deletePhoneTransfer(id);

        return new ResponseEntity<>(phoneTransferDTO, HttpStatus.ACCEPTED);
    }
}

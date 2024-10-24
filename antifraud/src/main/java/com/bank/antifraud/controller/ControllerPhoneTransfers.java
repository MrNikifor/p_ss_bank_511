package com.bank.antifraud.controller;

import com.bank.antifraud.dto.PhoneTransfersDTO;
import com.bank.antifraud.service.SuspiciousPhoneTransfersService;
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
@RequestMapping("/phone_transfers")
@AllArgsConstructor
public class ControllerPhoneTransfers {

    private final SuspiciousPhoneTransfersService phoneService;

    @GetMapping("/{id}")
    @Operation(summary = "Get phone transfer by ID")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of phone transfer")
    @ApiResponse(responseCode = "404", description = "phone transfer not found")
    public PhoneTransfersDTO getSAT(@PathVariable Long id) {
        return phoneService.getPhoneTransfer(id);
    }

    @GetMapping()
    @Operation(summary = "Get all phone transfers")
    @ApiResponse(responseCode = "200", description = "List of all phone transfers")
    public List<PhoneTransfersDTO> getAllSAT() {
        return phoneService.getListPhoneTransfers();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete phone transfer by ID")
    @ApiResponse(responseCode = "200", description = "phone transfer deleted successfully")
    @ApiResponse(responseCode = "404", description = "phone transfer not found")
    public void deleteSAT(@PathVariable Long id) {
        phoneService.delete(id);
    }

    @PostMapping()
    @Operation(summary = "Save new phone transfer")
    @ApiResponse(responseCode = "201", description = "New phone transfer saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public void saveSAT(@RequestBody PhoneTransfersDTO phoneTransfersDTO) {
        phoneService.savePhone(phoneTransfersDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing phone transfer")
    @ApiResponse(responseCode = "200", description = "phone transfer updated successfully")
    @ApiResponse(responseCode = "404", description = "phone transfer not found")
    public void updateSAT(@RequestBody PhoneTransfersDTO phoneTransfersDTO, @PathVariable Long id) {
        phoneService.updatePhone(phoneTransfersDTO, id);
    }
}

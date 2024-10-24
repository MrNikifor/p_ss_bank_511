package com.bank.antifraud.controller;

import com.bank.antifraud.dto.CardTransfersDTO;
import com.bank.antifraud.service.SuspiciousCardTransfersService;
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
@RequestMapping("/card_transfers")
public class ControllerCardTransfers {

    private final SuspiciousCardTransfersService cardService;

    @GetMapping("/{id}")
    @Operation(summary = "Get card transfer by ID")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of card transfer")
    @ApiResponse(responseCode = "404", description = "card transfer not found")
    public CardTransfersDTO getSAT(@PathVariable Long id) {
        return cardService.getCardTransfer(id);
    }

    @GetMapping()
    @Operation(summary = "Get all card transfers")
    @ApiResponse(responseCode = "200", description = "List of all card transfers")
    public List<CardTransfersDTO> getAllSAT() {
        return cardService.getListSCardTransfers();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete card transfer by ID")
    @ApiResponse(responseCode = "200", description = "card transfer deleted successfully")
    @ApiResponse(responseCode = "404", description = "card transfer not found")
    public void deleteSAT(@PathVariable Long id) {
        cardService.delete(id);
    }

    @PostMapping()
    @Operation(summary = "Save new card transfer")
    @ApiResponse(responseCode = "201", description = "New card transfer saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public void saveSAT(@RequestBody CardTransfersDTO cardTransfersDTO) {
        cardService.saveCard(cardTransfersDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing card transfer")
    @ApiResponse(responseCode = "200", description = "card transfer updated successfully")
    @ApiResponse(responseCode = "404", description = "card transfer not found")
    public void updateSAT(@RequestBody CardTransfersDTO cardTransfersDTO, @PathVariable Long id) {
        cardService.updateCard(cardTransfersDTO, id);
    }
}

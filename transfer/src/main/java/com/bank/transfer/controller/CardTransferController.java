package com.bank.transfer.controller;

import com.bank.transfer.dto.CardTransferDTO;
import com.bank.transfer.service.CardTransferService;
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
@Tag(name = "Card Transfer", description = "API for managing card transfers")
@RestController
@RequestMapping("/card-transfer")
public class CardTransferController {

    private final CardTransferService cardTransferService;

    @Autowired
    public CardTransferController(CardTransferService cardTransferService) {
        this.cardTransferService = cardTransferService;
    }

    @Operation(summary = "Get a list of all Card transfers", description = "Return a list of all Card transfers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "No card transfers found"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")})
    @GetMapping
    public ResponseEntity<List<CardTransferDTO>> getCardTransfersList() {

        log.info("Getting the card transfer list");

        return new ResponseEntity<>(cardTransferService.getAllCardTransfers(), HttpStatus.OK);
    }

    @Operation(summary = "Getting the card transfer by ID", description = "Returns the card transfer by the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "The Card transfer was not found by the received ID"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")})
    @GetMapping("/{id}")
    public ResponseEntity<CardTransferDTO> getCardTransferById(@PathVariable Long id) {

        log.info("Getting the card transfer by id: {}", id);

        CardTransferDTO cardTransferDTO = cardTransferService.getCardTransferById(id);

        return new ResponseEntity<>(cardTransferDTO, HttpStatus.OK);
    }

    @Operation(summary = "Creating the card transfer", description = "Return the created Card transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Card transfer created"),
            @ApiResponse(responseCode = "409", description = "Conflict of unique values during creation"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")})
    @PostMapping
    public ResponseEntity<CardTransferDTO> createCardTransfer(@RequestBody CardTransferDTO cardTransferDTO) {

        log.info("Creating card transfer by data: {}", cardTransferDTO);

        CardTransferDTO createdCardTransfer = cardTransferService.createCardTransfer(cardTransferDTO);

        return new ResponseEntity<>(createdCardTransfer, HttpStatus.CREATED);
    }

    @Operation(summary = "Updating the card transfer by ID", description = "Return the updated Card transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Card transfer updated"),
            @ApiResponse(responseCode = "409", description = "Conflict of unique values during the update"),
            @ApiResponse(responseCode = "404", description = "The card transfer was not found by the received ID"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")})
    @PutMapping("/{id}")
    public ResponseEntity<CardTransferDTO> updateCardTransfer(
            @PathVariable Long id, @RequestBody CardTransferDTO cardTransferDTO) {

        log.info("Updating card transfer by id {} and by data: {}", id, cardTransferDTO);

        CardTransferDTO updatedCardTransferDTO = cardTransferService.updateCardTransfer(cardTransferDTO, id);

        return new ResponseEntity<>(updatedCardTransferDTO, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Deleting the card transfer by ID", description = "Return the deleted Card transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Card transfer deleted"),
            @ApiResponse(responseCode = "404", description = "The Card transfer was not found by the received ID"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")})
    @DeleteMapping("/{id}")
    public ResponseEntity<CardTransferDTO> deleteCardTransfer(@PathVariable Long id) {

        log.info("Deleting card transfer by id {}", id);

        CardTransferDTO cardTransferDTO = cardTransferService.deleteCardTransfer(id);

        return new ResponseEntity<>(cardTransferDTO, HttpStatus.ACCEPTED);
    }
}

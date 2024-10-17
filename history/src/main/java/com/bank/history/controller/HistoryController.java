package com.bank.history.controller;

import com.bank.history.dto.HistoryDTO;
import com.bank.history.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @Operation(summary = "Add a new history record")
    @ApiResponse(responseCode = "201", description = "Created")
    @PostMapping
    public ResponseEntity<Long> addHistory(@RequestBody HistoryDTO historyDTO) {
        Long id = historyService.add(historyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @Operation(summary = "Get history by ID")
    @ApiResponse(responseCode = "200", description = "Found")
    @GetMapping("/{id}")
    public ResponseEntity<HistoryDTO> getHistoryById(@PathVariable Long id) {
        HistoryDTO historyDTO = historyService.getById(id);
        return ResponseEntity.ok(historyDTO);
    }

    @Operation(summary = "Update an existing history record")
    @ApiResponse(responseCode = "200", description = "Updated")
    @PutMapping
    public ResponseEntity<Void> updateHistory(@RequestBody HistoryDTO historyDTO) {
        historyService.update(historyDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a history record by ID")
    @ApiResponse(responseCode = "204", description = "No Content")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistoryById(@PathVariable Long id) {
        historyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Get all histories")
    @ApiResponse(responseCode = "200", description = "List of histories")
    @GetMapping
    public ResponseEntity<List<HistoryDTO>> getAllHistories() {
        List<HistoryDTO> histories = historyService.findAll();
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }
}
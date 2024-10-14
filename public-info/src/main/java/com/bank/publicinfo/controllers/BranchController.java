package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.BranchDTO;
import com.bank.publicinfo.service.BranchService;
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
@RequestMapping("/branches")
@Validated
@Slf4j
public class BranchController {

    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @Operation(summary = "Создать новое отделение банка")
    @ApiResponse(responseCode = "201", description = "Отделение банка успешно создано")
    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(@Valid @RequestBody BranchDTO branchDTO) {
        log.info("Creating branch with data: {}", branchDTO);
        BranchDTO createdBranch = branchService.createBranch(branchDTO);
        log.info("Branch created successfully with ID: {}", createdBranch.getId());
        return new ResponseEntity<>(createdBranch, HttpStatus.CREATED);
    }

    @Operation(summary = "Получить отделение банка по ID")
    @ApiResponse(responseCode = "200", description = "Отделение банка успешно получено")
    @ApiResponse(responseCode = "404", description = "Отделение банка не найдено")
    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable Long id) {
        log.info("Fetching branch with ID: {}", id);
        BranchDTO branch = branchService.getBranchById(id);
        log.info("Branch retrieved successfully: {}", branch);
        return new ResponseEntity<>(branch, HttpStatus.OK);
    }

    @Operation(summary = "Получить список всех отделений банка")
    @ApiResponse(responseCode = "200", description = "Список отделений банка успешно получен")
    @GetMapping
    public ResponseEntity<List<BranchDTO>> getAllBranches() {
        log.info("Fetching all branches");
        List<BranchDTO> branches = branchService.getAllBranches();
        log.info("Total branches retrieved: {}", branches.size());
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }

    @Operation(summary = "Обновить информацию об отделении банка")
    @ApiResponse(responseCode = "204", description = "Информация об отделении банка успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Отделение банка не найдено")
    @PutMapping("/{id}")
    public ResponseEntity<BranchDTO> updateBranch(@PathVariable Long id,
                                                  @Valid @RequestBody BranchDTO branchDTO) {
        log.info("Updating branch with ID: {}", id);
        branchDTO.setId(id);
        BranchDTO updatedBranch = branchService.updateBranch(branchDTO);
        log.info("Branch updated successfully with ID: {}", updatedBranch.getId());
        return new ResponseEntity<>(updatedBranch, HttpStatus.OK);
    }

    @Operation(summary = "Удалить отделение банка по ID")
    @ApiResponse(responseCode = "204", description = "Отделение банка успешно удалено")
    @ApiResponse(responseCode = "404", description = "Отделение банка не найдено")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        log.info("Deleting branch with ID: {}", id);
        branchService.deleteBranch(id);
        log.info("Branch deleted successfully with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.service.LicenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
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

import java.util.List;

@RestController
@RequestMapping("/licenses")
@Validated
@Slf4j
public class LicenseController {

    private final LicenseService licenseService;

    @Autowired
    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @Operation(summary = "Создать новую лицензию")
    @ApiResponse(responseCode = "201", description = "Лицензия успешно создана")
    @PostMapping
    public ResponseEntity<LicenseDto> createLicense(@Valid @RequestBody LicenseDto licenseDto) {

        log.info("Creating license with data: {}", licenseDto);

        LicenseDto createdLicense = licenseService.createLicense(licenseDto);

        log.info("License created successfully with ID: {}", createdLicense.getId());

        return new ResponseEntity<>(createdLicense, HttpStatus.CREATED);
    }

    @Operation(summary = "Получить лицензию по ID")
    @ApiResponse(responseCode = "200", description = "Лицензия успешно получена")
    @ApiResponse(responseCode = "404", description = "Лицензия не найдена")
    @GetMapping("/{id}")
    public ResponseEntity<LicenseDto> getLicenseById(@PathVariable Long id) {

        log.info("Fetching license with ID: {}", id);

        LicenseDto license = licenseService.getLicenseById(id);

        log.info("License retrieved successfully: {}", license);

        return new ResponseEntity<>(license, HttpStatus.OK);
    }

    @Operation(summary = "Получить список всех лицензий")
    @ApiResponse(responseCode = "200", description = "Список лицензий успешно получен")
    @GetMapping
    public ResponseEntity<List<LicenseDto>> getAllLicenses() {

        log.info("Fetching all licenses");

        List<LicenseDto> licenses = licenseService.getAllLicenses();

        log.info("Total licenses retrieved: {}", licenses.size());

        return new ResponseEntity<>(licenses, HttpStatus.OK);
    }

    @Operation(summary = "Обновить информацию о лицензии")
    @ApiResponse(responseCode = "204", description = "Информация о лицензии успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Лицензия не найдена")
    @PutMapping("/{id}")
    public ResponseEntity<LicenseDto> updateLicense(@PathVariable Long id,
                                                    @Valid @RequestBody LicenseDto licenseDto) {
        log.info("Updating license with ID: {}", id);

        licenseDto.setId(id);
        LicenseDto updatedLicense = licenseService.updateLicense(licenseDto);

        log.info("License updated successfully with ID: {}", updatedLicense.getId());

        return new ResponseEntity<>(updatedLicense, HttpStatus.OK);
    }

    @Operation(summary = "Удалить лицензию по ID")
    @ApiResponse(responseCode = "204", description = "Лицензия успешно удалена")
    @ApiResponse(responseCode = "404", description = "Лицензия не найдена")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLicense(@PathVariable Long id) {

        log.info("Deleting license with ID: {}", id);

        licenseService.deleteLicense(id);

        log.info("License deleted successfully with ID: {}", id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


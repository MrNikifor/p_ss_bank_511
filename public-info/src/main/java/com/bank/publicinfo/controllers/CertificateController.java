package com.bank.publicinfo.controllers;

import com.bank.publicinfo.dto.CertificateDTO;
import com.bank.publicinfo.service.CertificateService;
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
@RequestMapping("/certificates")
@Validated
@Slf4j
public class CertificateController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @Operation(summary = "Создать новый сертификат")
    @ApiResponse(responseCode = "201", description = "Сертификат успешно создан")
    @PostMapping
    public ResponseEntity<CertificateDTO> createCertificate(@Valid @RequestBody CertificateDTO certificateDto) {

        log.info("Creating certificate with data: {}", certificateDto);

        CertificateDTO createdCertificate = certificateService.createCertificate(certificateDto);

        log.info("Certificate created successfully with ID: {}", createdCertificate.getId());

        return new ResponseEntity<>(createdCertificate, HttpStatus.CREATED);
    }

    @Operation(summary = "Получить сертификат по ID")
    @ApiResponse(responseCode = "200", description = "Сертификат успешно получен")
    @ApiResponse(responseCode = "404", description = "Сертификат не найден")
    @GetMapping("/{id}")
    public ResponseEntity<CertificateDTO> getCertificateById(@PathVariable Long id) {

        log.info("Fetching certificate with ID: {}", id);

        CertificateDTO certificate = certificateService.getCertificateById(id);

        log.info("Certificate retrieved successfully: {}", certificate);

        return new ResponseEntity<>(certificate, HttpStatus.OK);
    }

    @Operation(summary = "Получить список всех сертификатов")
    @ApiResponse(responseCode = "200", description = "Список сертификатов успешно получен")
    @GetMapping
    public ResponseEntity<List<CertificateDTO>> getAllCertificates() {

        log.info("Fetching all certificates");

        List<CertificateDTO> certificates = certificateService.getAllCertificates();

        log.info("Total certificates retrieved: {}", certificates.size());

        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @Operation(summary = "Обновить информацию о сертификате")
    @ApiResponse(responseCode = "204", description = "Информация о сертификате успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Сертификат не найден")
    @PutMapping("/{id}")
    public ResponseEntity<CertificateDTO> updateCertificate(@PathVariable Long id,
                                                            @Valid @RequestBody CertificateDTO certificateDto) {
        log.info("Updating certificate with ID: {}", id);

        certificateDto.setId(id);
        CertificateDTO updatedCertificate = certificateService.updateCertificate(certificateDto);

        log.info("Certificate updated successfully with ID: {}", updatedCertificate.getId());

        return new ResponseEntity<>(updatedCertificate, HttpStatus.OK);
    }

    @Operation(summary = "Удалить сертификат по ID")
    @ApiResponse(responseCode = "204", description = "Сертификат успешно удален")
    @ApiResponse(responseCode = "404", description = "Сертификат не найден")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable Long id) {

        log.info("Deleting certificate with ID: {}", id);

        certificateService.deleteCertificate(id);

        log.info("Certificate deleted successfully with ID: {}", id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


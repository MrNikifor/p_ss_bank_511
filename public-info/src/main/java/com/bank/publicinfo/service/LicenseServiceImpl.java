package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.exception.ResourceNotFoundException;
import com.bank.publicinfo.mapper.AutoLicenseMapper;
import com.bank.publicinfo.model.BankDetails;
import com.bank.publicinfo.model.License;
import com.bank.publicinfo.repositories.BankDetailsRepository;
import com.bank.publicinfo.repositories.LicenseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;
    private final BankDetailsRepository bankDetailsRepository;
    private final AutoLicenseMapper autoLicenseMapper = AutoLicenseMapper.LICENSE_MAPPER;

    @Override
    @Transactional
    public LicenseDto createLicense(LicenseDto licenseDTO) {
        log.info("Creating new license: {}", licenseDTO);
        validateLicenseDTO(licenseDTO);

        Long bankDetailsId = licenseDTO.getBankDetailsId();
        BankDetails bankDetails = bankDetailsRepository.findById(bankDetailsId)
                .orElseThrow(() -> {
                    log.error("Bank details not found for ID: {}", bankDetailsId);
                    return new ResourceNotFoundException("Bank details not found");
                });

        License license = autoLicenseMapper.mapToLicense(licenseDTO);
        license.setBankDetails(bankDetails);
        License savedLicense = licenseRepository.save(license);

        log.info("License successfully created: {}", savedLicense);
        return autoLicenseMapper.mapToLicenseDTO(savedLicense);
    }

    @Override
    @Transactional(readOnly = true)
    public LicenseDto getLicenseById(Long id) {
        log.info("Fetching license by ID: {}", id);

        Long validatedId = Optional.ofNullable(id)
                .orElseThrow(() -> {
                    log.error("ID must not be null");
                    return new IllegalArgumentException("ID must not be null");
                });

        License license = licenseRepository.findById(validatedId)
                .orElseThrow(() -> {
                    log.error("License not found for ID: {}", validatedId);
                    return new ResourceNotFoundException("License not found");
                });

        log.info("License found: {}", license);
        return autoLicenseMapper.mapToLicenseDTO(license);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LicenseDto> getAllLicenses() {
        log.info("Fetching all licenses");

        List<License> licenses = licenseRepository.findAll();
        log.info("Total licenses fetched: {}", licenses.size());

        return licenses.stream()
                .map(autoLicenseMapper::mapToLicenseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LicenseDto updateLicense(LicenseDto licenseDTO) {
        log.info("Updating license: {}", licenseDTO);
        validateLicenseDTO(licenseDTO);

        Long validatedId = Optional.ofNullable(licenseDTO.getId())
                .orElseThrow(() -> {
                    log.error("ID must not be null");
                    return new IllegalArgumentException("ID must not be null");
                });

        License existingLicense = licenseRepository.findById(validatedId)
                .orElseThrow(() -> {
                    log.error("License not found for ID: {}", validatedId);
                    return new ResourceNotFoundException("License not found");
                });

        existingLicense.setPhoto(licenseDTO.getPhoto());
        License updatedLicense = licenseRepository.save(existingLicense);

        log.info("License successfully updated: {}", updatedLicense);
        return autoLicenseMapper.mapToLicenseDTO(updatedLicense);
    }

    @Override
    @Transactional
    public void deleteLicense(Long id) {
        log.info("Deleting license with ID: {}", id);

        Long validatedId = Optional.ofNullable(id)
                .orElseThrow(() -> {
                    log.error("ID must not be null");
                    return new IllegalArgumentException("ID must not be null");
                });

        if (!licenseRepository.existsById(validatedId)) {
            log.error("No license found with ID: {}", validatedId);
            throw new ResourceNotFoundException("License not found");
        }

        licenseRepository.deleteById(validatedId);
        log.info("License with ID {} successfully deleted", validatedId);
    }

    private void validateLicenseDTO(LicenseDto licenseDTO) {
        if (licenseDTO == null) {
            log.error("Failed to process license: LicenseDTO is null");
            throw new IllegalArgumentException("LicenseDTO must not be null");
        }
    }
}
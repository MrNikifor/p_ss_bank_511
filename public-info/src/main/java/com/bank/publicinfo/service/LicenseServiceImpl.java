package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.LicenseDTO;
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
    public LicenseDTO createLicense(LicenseDTO licenseDTO) {
        log.info("Creating new license: {}", licenseDTO);

        if (licenseDTO == null) {
            log.error("Failed to create license: LicenseDTO is null");
            throw new IllegalArgumentException("LicenseDTO must not be null");
        }

        Long bankDetailsId = licenseDTO.getBankDetailsId();
        if (bankDetailsId == null) {
            log.error("Failed to create license: BankDetailsId is null");
            throw new IllegalArgumentException("BankDetailsId must not be null");
        }

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
    public LicenseDTO getLicenseById(Long id) {
        log.info("Fetching license by ID: {}", id);

        if (id == null) {
            log.error("ID must not be null");
            throw new IllegalArgumentException("ID must not be null");
        }

        License license = licenseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("License not found for ID: {}", id);
                    return new ResourceNotFoundException("License not found");
                });

        log.info("License found: {}", license);
        return autoLicenseMapper.mapToLicenseDTO(license);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LicenseDTO> getAllLicenses() {
        log.info("Fetching all licenses");

        List<License> licenses = licenseRepository.findAll();
        log.info("Total licenses fetched: {}", licenses.size());

        return licenses.stream()
                .map(autoLicenseMapper::mapToLicenseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LicenseDTO updateLicense(LicenseDTO licenseDTO) {
        log.info("Updating license: {}", licenseDTO);

        if (licenseDTO == null) {
            log.error("Failed to update license: LicenseDTO is null");
            throw new IllegalArgumentException("LicenseDTO must not be null");
        }

        Long id = licenseDTO.getId();
        if (id == null) {
            log.error("ID must not be null");
            throw new IllegalArgumentException("ID must not be null");
        }

        License existingLicense = licenseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("License not found for ID: {}", id);
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

        if (id == null) {
            log.error("ID must not be null");
            throw new IllegalArgumentException("ID must not be null");
        }

        if (!licenseRepository.existsById(id)) {
            log.error("No license found with ID: {}", id);
            throw new ResourceNotFoundException("License not found");
        }

        licenseRepository.deleteById(id);
        log.info("License with ID {} successfully deleted", id);
    }
}
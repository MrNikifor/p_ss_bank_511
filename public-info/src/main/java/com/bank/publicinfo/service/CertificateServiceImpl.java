package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.exception.ResourceNotFoundException;
import com.bank.publicinfo.mapper.AutoCertificateMapper;
import com.bank.publicinfo.model.BankDetails;
import com.bank.publicinfo.model.Certificate;
import com.bank.publicinfo.repositories.BankDetailsRepository;
import com.bank.publicinfo.repositories.CertificateRepository;
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
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final BankDetailsRepository bankDetailsRepository;
    private final AutoCertificateMapper autoCertificateMapper = AutoCertificateMapper.CERTIFICATE_MAPPER;

    @Override
    @Transactional
    public CertificateDto createCertificate(CertificateDto certificateDTO) {
        log.info("Creating new certificate: {}", certificateDTO);
        validateCertificateDTO(certificateDTO);

        Long bankDetailsId = certificateDTO.getBankDetailsId();
        BankDetails bankDetails = bankDetailsRepository.findById(bankDetailsId)
                .orElseThrow(() -> {
                    log.error("Bank details not found for ID: {}", bankDetailsId);
                    return new ResourceNotFoundException("Bank details not found");
                });

        Certificate certificate = autoCertificateMapper.mapToCertificate(certificateDTO);
        certificate.setBankDetails(bankDetails);
        Certificate savedCertificate = certificateRepository.save(certificate);

        log.info("Certificate successfully created: {}", savedCertificate);
        return autoCertificateMapper.mapToCertificateDTO(savedCertificate);
    }

    @Override
    @Transactional(readOnly = true)
    public CertificateDto getCertificateById(Long id) {
        log.info("Fetching certificate by ID: {}", id);

        Long validatedId = Optional.ofNullable(id)
                .orElseThrow(() -> {
                    log.error("ID must not be null");
                    return new IllegalArgumentException("ID must not be null");
                });

        Certificate certificate = certificateRepository.findById(validatedId)
                .orElseThrow(() -> {
                    log.error("Certificate not found for ID: {}", validatedId);
                    return new ResourceNotFoundException("Certificate not found");
                });

        log.info("Certificate found: {}", certificate);
        return autoCertificateMapper.mapToCertificateDTO(certificate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDto> getAllCertificates() {
        log.info("Fetching all certificates");

        List<Certificate> certificates = certificateRepository.findAll();
        log.info("Total certificates fetched: {}", certificates.size());

        return certificates.stream()
                .map(autoCertificateMapper::mapToCertificateDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CertificateDto updateCertificate(CertificateDto certificateDTO) {
        log.info("Updating certificate: {}", certificateDTO);
        validateCertificateDTO(certificateDTO);

        Long validatedId = Optional.ofNullable(certificateDTO.getId())
                .orElseThrow(() -> {
                    log.error("ID must not be null");
                    return new IllegalArgumentException("ID must not be null");
                });

        Certificate existingCertificate = certificateRepository.findById(validatedId)
                .orElseThrow(() -> {
                    log.error("Certificate not found for ID: {}", validatedId);
                    return new ResourceNotFoundException("Certificate not found");
                });

        existingCertificate.setPhoto(certificateDTO.getPhoto());
        Certificate updatedCertificate = certificateRepository.save(existingCertificate);

        log.info("Certificate successfully updated: {}", updatedCertificate);
        return autoCertificateMapper.mapToCertificateDTO(updatedCertificate);
    }

    @Override
    @Transactional
    public void deleteCertificate(Long id) {
        log.info("Deleting certificate with ID: {}", id);

        Long validatedId = Optional.ofNullable(id)
                .orElseThrow(() -> {
                    log.error("ID must not be null");
                    return new IllegalArgumentException("ID must not be null");
                });

        if (!certificateRepository.existsById(validatedId)) {
            log.error("No certificate found with ID: {}", validatedId);
            throw new ResourceNotFoundException("Certificate not found");
        }

        certificateRepository.deleteById(validatedId);
        log.info("Certificate with ID {} successfully deleted", validatedId);
    }

    private void validateCertificateDTO(CertificateDto certificateDTO) {
        if (certificateDTO == null) {
            log.error("Failed to process certificate: CertificateDTO is null");
            throw new IllegalArgumentException("CertificateDTO must not be null");
        }
    }
}

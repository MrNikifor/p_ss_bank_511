package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.CertificateDTO;
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
    public CertificateDTO createCertificate(CertificateDTO certificateDTO) {

        log.info("Creating new certificate: {}", certificateDTO);

        if (certificateDTO == null) {

            log.error("Failed to create certificate: CertificateDTO is null");
            throw new IllegalArgumentException("CertificateDTO must not be null");
        }


        Long bankDetailsId = certificateDTO.getBankDetailsId();
        if (bankDetailsId == null) {

            log.error("Failed to create certificate: BankDetails ID is null");
            throw new IllegalArgumentException("BankDetails ID must not be null");
        }

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
    public CertificateDTO getCertificateById(Long id) {

        log.info("Fetching certificate by ID: {}", id);

        if (id == null) {
            log.error("ID must not be null");
            throw new IllegalArgumentException("ID must not be null");
        }

        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Certificate not found for ID: {}", id);
                    return new ResourceNotFoundException("Certificate not found");
                });

        log.info("Certificate found: {}", certificate);

        return autoCertificateMapper.mapToCertificateDTO(certificate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDTO> getAllCertificates() {

        log.info("Fetching all certificates");

        List<Certificate> certificates = certificateRepository.findAll();

        log.info("Total certificates fetched: {}", certificates.size());

        return certificates.stream()
                .map(autoCertificateMapper::mapToCertificateDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CertificateDTO updateCertificate(CertificateDTO certificateDTO) {

        log.info("Updating certificate: {}", certificateDTO);

        if (certificateDTO == null) {
            log.error("Failed to update certificate: CertificateDTO is null");
            throw new IllegalArgumentException("CertificateDTO must not be null");
        }

        Long id = certificateDTO.getId();

        if (id == null) {
            log.error("ID must not be null");
            throw new IllegalArgumentException("ID must not be null");
        }

        Certificate existingCertificate = certificateRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Certificate not found for ID: {}", id);

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

        if (id == null) {
            log.error("ID must not be null");
            throw new IllegalArgumentException("ID must not be null");
        }

        if (!certificateRepository.existsById(id)) {
            log.error("No certificate found with ID: {}", id);
            throw new ResourceNotFoundException("Certificate not found");
        }

        certificateRepository.deleteById(id);

        log.info("Certificate with ID {} successfully deleted", id);
    }
}

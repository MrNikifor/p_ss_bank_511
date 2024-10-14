package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.CertificateDTO;

import java.util.List;

public interface CertificateService {
    CertificateDTO createCertificate(CertificateDTO certificateDTO);
    CertificateDTO getCertificateById(Long id);
    List<CertificateDTO> getAllCertificates();
    CertificateDTO updateCertificate(CertificateDTO certificateDTO);
    void deleteCertificate(Long id);
}


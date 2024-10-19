package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.CertificateDto;

import java.util.List;

public interface CertificateService {
    CertificateDto createCertificate(CertificateDto certificateDTO);
    CertificateDto getCertificateById(Long id);
    List<CertificateDto> getAllCertificates();
    CertificateDto updateCertificate(CertificateDto certificateDTO);
    void deleteCertificate(Long id);
}


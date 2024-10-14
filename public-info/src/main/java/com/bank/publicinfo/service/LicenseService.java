package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.LicenseDTO;

import java.util.List;

public interface LicenseService {
    LicenseDTO createLicense(LicenseDTO licenseDTO);
    LicenseDTO getLicenseById(Long id);
    List<LicenseDTO> getAllLicenses();
    LicenseDTO updateLicense(LicenseDTO licenseDTO);
    void deleteLicense(Long id);
}


package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.LicenseDto;

import java.util.List;

public interface LicenseService {
    LicenseDto createLicense(LicenseDto licenseDTO);
    LicenseDto getLicenseById(Long id);
    List<LicenseDto> getAllLicenses();
    LicenseDto updateLicense(LicenseDto licenseDTO);
    void deleteLicense(Long id);
}


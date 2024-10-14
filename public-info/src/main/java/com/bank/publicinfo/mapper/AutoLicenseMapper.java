package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.LicenseDTO;
import com.bank.publicinfo.model.License;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoLicenseMapper {

    AutoLicenseMapper LICENSE_MAPPER = Mappers.getMapper(AutoLicenseMapper.class);

    LicenseDTO mapToLicenseDTO(License license);

    License mapToLicense(LicenseDTO licenseDTO);
}
package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.model.License;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoLicenseMapper {

    AutoLicenseMapper LICENSE_MAPPER = Mappers.getMapper(AutoLicenseMapper.class);

    @Mapping(target = "id", ignore = true)
    License mapToLicense(LicenseDto licenseDTO);

    LicenseDto mapToLicenseDTO(License license);
}
package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.model.Certificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoCertificateMapper {

    AutoCertificateMapper CERTIFICATE_MAPPER = Mappers.getMapper(AutoCertificateMapper.class);

    @Mapping(target = "id", ignore = true)
    Certificate mapToCertificate(CertificateDto certificateDto);

    CertificateDto mapToCertificateDTO(Certificate certificate);
}

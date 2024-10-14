package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.CertificateDTO;
import com.bank.publicinfo.model.Certificate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoCertificateMapper {

    AutoCertificateMapper CERTIFICATE_MAPPER = Mappers.getMapper(AutoCertificateMapper.class);

    CertificateDTO mapToCertificateDTO(Certificate certificate);

    Certificate mapToCertificate(CertificateDTO certificateDTO);
}

package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.PhoneTransfersDTO;
import com.bank.antifraud.model.SuspiciousPhoneTransfers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PhoneTransferMapper {

    PhoneTransferMapper INSTANCE = Mappers.getMapper(PhoneTransferMapper.class);

    @Mapping(target = "id", ignore = true)
    SuspiciousPhoneTransfers toEntity(PhoneTransfersDTO phoneTransferDTO);

    PhoneTransfersDTO toDTO(SuspiciousPhoneTransfers phoneTransferEntity);

    List<PhoneTransfersDTO> toDTOList(List<SuspiciousPhoneTransfers> phoneTransferEntityList);

    void updateEntityFromDTO(PhoneTransfersDTO phoneTransferDTO, @MappingTarget SuspiciousPhoneTransfers phoneTransferEntity);

}

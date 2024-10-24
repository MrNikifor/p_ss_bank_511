package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.CardTransfersDTO;
import com.bank.antifraud.model.SuspiciousCardTransfers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CardTransferMapper {

    CardTransferMapper INSTANCE = Mappers.getMapper(CardTransferMapper.class);

    @Mapping(target = "id", ignore = true)
    SuspiciousCardTransfers toEntity(CardTransfersDTO cardTransferDTO);

    CardTransfersDTO toDTO(SuspiciousCardTransfers cardTransferEntity);

    List<CardTransfersDTO> toDTOList(List<SuspiciousCardTransfers> cardTransferEntityList);

    void updateEntityFromDTO(CardTransfersDTO transferDTO, @MappingTarget SuspiciousCardTransfers cardTransferEntity);
}

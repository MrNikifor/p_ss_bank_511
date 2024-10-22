package com.bank.transfer.mapper;

import com.bank.transfer.dto.CardTransferDTO;
import com.bank.transfer.entity.CardTransferEntity;
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
    CardTransferEntity toEntity(CardTransferDTO cardTransferDTO);

    CardTransferDTO toDTO(CardTransferEntity cardTransferEntity);

    List<CardTransferDTO> toDTOList(List<CardTransferEntity> cardTransferEntityList);

    void updateEntityFromDTO(CardTransferDTO transferDTO, @MappingTarget CardTransferEntity cardTransferEntity);
}

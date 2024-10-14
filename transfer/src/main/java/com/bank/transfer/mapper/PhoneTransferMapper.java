package com.bank.transfer.mapper;

import com.bank.transfer.dto.PhoneTransferDTO;
import com.bank.transfer.entity.PhoneTransferEntity;
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
    PhoneTransferEntity toEntity(PhoneTransferDTO phoneTransferDTO);

    PhoneTransferDTO toDTO(PhoneTransferEntity phoneTransferEntity);

    List<PhoneTransferDTO> toDTOList(List<PhoneTransferEntity> phoneTransferEntityList);

    void updateEntityFromDTO(PhoneTransferDTO phoneTransferDTO, @MappingTarget PhoneTransferEntity phoneTransferEntity);
}

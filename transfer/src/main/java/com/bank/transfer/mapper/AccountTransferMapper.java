package com.bank.transfer.mapper;

import com.bank.transfer.dto.AccountTransferDTO;
import com.bank.transfer.entity.AccountTransferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountTransferMapper {

    AccountTransferMapper INSTANCE = Mappers.getMapper(AccountTransferMapper.class);

    @Mapping(target = "id", ignore = true)
    AccountTransferEntity toEntity(AccountTransferDTO accountTransferDTO);

    AccountTransferDTO toDTO(AccountTransferEntity accountTransferEntity);

    List<AccountTransferDTO> toDTOList(List<AccountTransferEntity> accountTransferEntityList);

    void updateEntityFromDTO(AccountTransferDTO accountTransferDTO,
                             @MappingTarget AccountTransferEntity accountTransferEntity);
}

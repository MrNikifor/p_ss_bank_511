package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.AccountTransfersDTO;
import com.bank.antifraud.model.SuspiciousAccountTransfers;
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
    @Mapping(target = "account_transfer_id", source = "accountTransferDTO.account_transfer_id")
    SuspiciousAccountTransfers toEntity(AccountTransfersDTO accountTransferDTO);

    AccountTransfersDTO toDTO(SuspiciousAccountTransfers accountTransferEntity);

    List<AccountTransfersDTO> toDTOList(List<SuspiciousAccountTransfers> accountTransferEntityList);

    void updateEntityFromDTO(AccountTransfersDTO accountTransferDTO,
                             @MappingTarget SuspiciousAccountTransfers accountTransferEntity);
}

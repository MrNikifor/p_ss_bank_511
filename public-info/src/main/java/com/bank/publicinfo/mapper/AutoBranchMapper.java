package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BranchDTO;
import com.bank.publicinfo.model.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoBranchMapper {

    AutoBranchMapper BRANCH_MAPPER = Mappers.getMapper(AutoBranchMapper.class);

    BranchDTO mapToBranchDTO(Branch branch);

    Branch mapToBranch(BranchDTO branchDTO);
}
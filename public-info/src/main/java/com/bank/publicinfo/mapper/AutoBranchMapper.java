package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.model.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoBranchMapper {

    AutoBranchMapper BRANCH_MAPPER = Mappers.getMapper(AutoBranchMapper.class);

    @Mapping(target = "id", ignore = true)
    Branch mapToBranch(BranchDto branchDto);

    BranchDto mapToBranchDto(Branch branch);

    void updateBranchFromDto(BranchDto branchDto, @MappingTarget Branch branch);
}
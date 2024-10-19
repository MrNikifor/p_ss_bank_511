package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BranchDto;

import java.util.List;

public interface BranchService {
    BranchDto createBranch(BranchDto branchDTO);
    BranchDto getBranchById(Long id);
    List<BranchDto> getAllBranches();
    BranchDto updateBranch(BranchDto branchDTO);
    void deleteBranch(Long id);
}

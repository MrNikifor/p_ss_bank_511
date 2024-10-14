package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BranchDTO;

import java.util.List;

public interface BranchService {
    BranchDTO createBranch(BranchDTO branchDTO);
    BranchDTO getBranchById(Long id);
    List<BranchDTO> getAllBranches();
    BranchDTO updateBranch(BranchDTO branchDTO);
    void deleteBranch(Long id);
}

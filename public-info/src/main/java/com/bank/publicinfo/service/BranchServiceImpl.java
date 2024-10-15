package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BranchDTO;
import com.bank.publicinfo.exception.ResourceNotFoundException;
import com.bank.publicinfo.mapper.AutoBranchMapper;
import com.bank.publicinfo.model.Branch;
import com.bank.publicinfo.repositories.BranchRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final AutoBranchMapper autoBranchMapper = AutoBranchMapper.BRANCH_MAPPER;

    @Override
    @Transactional
    public BranchDTO createBranch(BranchDTO branchDTO) {

        log.info("Creating new branch: {}", branchDTO);

        if (branchDTO == null) {
            log.error("Failed to create branch: BranchDTO is null");
            throw new IllegalArgumentException("BranchDTO must not be null");
        }

        Branch branch = autoBranchMapper.mapToBranch(branchDTO);
        Branch savedBranch = branchRepository.save(branch);

        log.info("Branch successfully created: {}", savedBranch);

        return autoBranchMapper.mapToBranchDTO(savedBranch);
    }

    @Override
    @Transactional(readOnly = true)
    public BranchDTO getBranchById(Long id) {
        log.info("Fetching branch by ID: {}", id);

        if (id == null) {
            log.error("ID must not be null");
            throw new IllegalArgumentException("ID must not be null");
        }

        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Branch not found for ID: {}", id);
                    return new ResourceNotFoundException("Branch not found");
                });

        log.info("Branch found: {}", branch);

        return autoBranchMapper.mapToBranchDTO(branch);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchDTO> getAllBranches() {

        log.info("Fetching all branches");

        List<Branch> branches = branchRepository.findAll();

        log.info("Total branches fetched: {}", branches.size());

        return branches.stream()
                .map(autoBranchMapper::mapToBranchDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BranchDTO updateBranch(BranchDTO branchDTO) {

        log.info("Updating branch: {}", branchDTO);

        if (branchDTO == null) {
            log.error("Failed to update branch: BranchDTO is null");
            throw new IllegalArgumentException("BranchDTO must not be null");
        }

        Long id = branchDTO.getId();
        if (id == null) {
            log.error("ID must not be null");
            throw new IllegalArgumentException("ID must not be null");
        }

        Branch existingBranch = branchRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Branch not found for ID: {}", id);

                    return new ResourceNotFoundException("Branch not found");
                });

        existingBranch.setAddress(branchDTO.getAddress());
        existingBranch.setPhoneNumber(branchDTO.getPhoneNumber());
        existingBranch.setCity(branchDTO.getCity());
        existingBranch.setStartOfWork(branchDTO.getStartOfWork());
        existingBranch.setEndOfWork(branchDTO.getEndOfWork());

        Branch updatedBranch = branchRepository.save(existingBranch);

        log.info("Branch successfully updated: {}", updatedBranch);

        return autoBranchMapper.mapToBranchDTO(updatedBranch);
    }

    @Override
    @Transactional
    public void deleteBranch(Long id) {
        log.info("Deleting branch with ID: {}", id);

        if (id == null) {
            log.error("ID must not be null");
            throw new IllegalArgumentException("ID must not be null");
        }

        if (!branchRepository.existsById(id)) {
            log.error("No branch found with ID: {}", id);
            throw new ResourceNotFoundException("Branch not found");
        }

        branchRepository.deleteById(id);

        log.info("Branch with ID {} successfully deleted", id);
    }
}
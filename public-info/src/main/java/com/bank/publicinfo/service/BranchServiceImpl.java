package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.exception.ResourceNotFoundException;
import com.bank.publicinfo.mapper.AutoBranchMapper;
import com.bank.publicinfo.model.Branch;
import com.bank.publicinfo.repositories.BranchRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final AutoBranchMapper autoBranchMapper = AutoBranchMapper.BRANCH_MAPPER;

    @Override
    @Transactional
    public BranchDto createBranch(BranchDto branchDTO) {
        log.info("Creating new branch: {}", branchDTO);
        validateBranchDTO(branchDTO);

        Branch branch = autoBranchMapper.mapToBranch(branchDTO);
        Branch savedBranch = branchRepository.save(branch);

        log.info("Branch successfully created: {}", savedBranch);
        return autoBranchMapper.mapToBranchDto(savedBranch);
    }

    @Override
    @Transactional(readOnly = true)
    public BranchDto getBranchById(Long id) {
        log.info("Fetching branch by ID: {}", id);

        Long validatedId = Optional.ofNullable(id)
                .orElseThrow(() -> {
                    log.error("ID must not be null");
                    return new ResourceNotFoundException("ID must not be null");
                });

        Branch branch = branchRepository.findById(validatedId)
                .orElseThrow(() -> {
                    log.error("Branch not found for ID: {}", validatedId);
                    return new ResourceNotFoundException("Branch not found");
                });

        log.info("Branch found: {}", branch);
        return autoBranchMapper.mapToBranchDto(branch);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchDto> getAllBranches() {
        log.info("Fetching all branches");

        List<Branch> branches = branchRepository.findAll();
        log.info("Total branches fetched: {}", branches.size());

        return branches.stream()
                .map(autoBranchMapper::mapToBranchDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BranchDto updateBranch(BranchDto branchDTO) {
        log.info("Updating branch: {}", branchDTO);
        validateBranchDTO(branchDTO);

        Long validatedId = Optional.ofNullable(branchDTO.getId())
                .orElseThrow(() -> {
                    log.error("ID must not be null");
                    return new ResourceNotFoundException("ID must not be null");
                });

        Branch existingBranch = branchRepository.findById(validatedId)
                .orElseThrow(() -> {
                    log.error("Branch not found for ID: {}", validatedId);
                    return new ResourceNotFoundException("Branch not found");
                });

        autoBranchMapper.updateBranchFromDto(branchDTO, existingBranch);
        Branch updatedBranch = branchRepository.save(existingBranch);

        log.info("Branch successfully updated: {}", updatedBranch);
        return autoBranchMapper.mapToBranchDto(updatedBranch);
    }

    @Override
    @Transactional
    public void deleteBranch(Long id) {
        log.info("Deleting branch with ID: {}", id);

        Long validatedId = Optional.ofNullable(id)
                .orElseThrow(() -> {
                    log.error("ID must not be null");
                    return new ResourceNotFoundException("ID must not be null");
                });

        if (!branchRepository.existsById(validatedId)) {
            log.error("No branch found with ID: {}", validatedId);
            throw new ResourceNotFoundException("Branch not found");
        }

        branchRepository.deleteById(validatedId);
        log.info("Branch with ID {} successfully deleted", validatedId);
    }

    private void validateBranchDTO(BranchDto branchDTO) {
        if (branchDTO == null) {
            log.error("Failed to process branch: BranchDTO is null");
            throw new ResourceNotFoundException("BranchDTO must not be null");
        }
    }
}
package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.exception.ResourceNotFoundException;
import com.bank.publicinfo.mapper.AutoBankDetailsMapper;
import com.bank.publicinfo.model.BankDetails;
import com.bank.publicinfo.repositories.BankDetailsRepository;
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
public class BankDetailsServiceImpl implements BankDetailsService {

    private final BankDetailsRepository bankDetailsRepository;

    private final AutoBankDetailsMapper autoBankDetailsMapper = AutoBankDetailsMapper.BANK_DETAILS_MAPPER;

    @Override
    @Transactional
    public BankDetailsDto createBankDetails(BankDetailsDto bankDetailsDTO) {

        log.info("Creating new bank details: {}", bankDetailsDTO);

        validateBankDetailsDTO(bankDetailsDTO);

        BankDetails bankDetails = autoBankDetailsMapper.mapToBankDetails(bankDetailsDTO);
        BankDetails savedBankDetails = bankDetailsRepository.save(bankDetails);

        log.info("Bank details successfully created: {}", savedBankDetails);

        return autoBankDetailsMapper.mapToBankDetailsDto(savedBankDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public BankDetailsDto getBankDetailsById(Long id) {

        log.info("Fetching bank details by ID: {}", id);

        Long validatedId = Optional.ofNullable(id)
                .orElseThrow(() -> {
                    log.error("ID must not be null");

                    return new IllegalArgumentException("ID must not be null");
                });

        BankDetails bankDetails = bankDetailsRepository.findById(validatedId)
                .orElseThrow(() -> {
                    log.error("Bank details not found for ID: {}", validatedId);

                    return new ResourceNotFoundException("Bank details not found");
                });

        log.info("Bank details found: {}", bankDetails);

        return autoBankDetailsMapper.mapToBankDetailsDto(bankDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankDetailsDto> getAllBankDetails() {

        log.info("Fetching all bank details");

        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();

        log.info("Total bank details fetched: {}", bankDetailsList.size());

        return bankDetailsList.stream()
                .map(autoBankDetailsMapper::mapToBankDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BankDetailsDto updateBankDetails(BankDetailsDto bankDetailsDTO) {

        log.info("Updating bank details: {}", bankDetailsDTO);

        validateBankDetailsDTO(bankDetailsDTO);
        Long id = bankDetailsDTO.getId();

        Long validatedId = Optional.ofNullable(id)
                .orElseThrow(() -> {
                    log.error("ID must not be null");

                    return new IllegalArgumentException("ID must not be null");
                });

        BankDetails existingBankDetail = bankDetailsRepository.findById(validatedId)
                .orElseThrow(() -> {
                    log.error("Bank details not found for ID: {}", validatedId);

                    return new ResourceNotFoundException("Bank details not found");
                });

        autoBankDetailsMapper.updateBankDetailsFromDto(bankDetailsDTO, existingBankDetail);

        BankDetails updatedBankDetail = bankDetailsRepository.save(existingBankDetail);

        log.info("Bank details successfully updated: {}", updatedBankDetail);

        return autoBankDetailsMapper.mapToBankDetailsDto(updatedBankDetail);
    }

    @Override
    @Transactional
    public void deleteBankDetails(Long id) {

        log.info("Deleting bank details with ID: {}", id);

        Long validatedId = Optional.ofNullable(id)
                .orElseThrow(() -> {
                    log.error("ID must not be null");

                    return new IllegalArgumentException("ID must not be null");
                });

        if (!bankDetailsRepository.existsById(validatedId)) {
            log.error("No bank details found with ID: {}", validatedId);

            throw new ResourceNotFoundException("Bank details not found");
        }

        bankDetailsRepository.deleteById(validatedId);

        log.info("Bank details with ID {} successfully deleted", validatedId);
    }

    private void validateBankDetailsDTO(BankDetailsDto bankDetailsDTO) {

        if (bankDetailsDTO == null) {
            log.error("Failed to process bank details: BankDetailsDTO is null");

            throw new IllegalArgumentException("Bank Details DTO must not be null");
        }
    }
}
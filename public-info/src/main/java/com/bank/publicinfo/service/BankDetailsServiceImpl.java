package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BankDetailsDTO;
import com.bank.publicinfo.exception.ResourceNotFoundException;
import com.bank.publicinfo.mapper.AutoBankDetailsMapper;
import com.bank.publicinfo.model.BankDetails;
import com.bank.publicinfo.repositories.BankDetailsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BankDetailsServiceImpl implements BankDetailsService {

    private final BankDetailsRepository bankDetailsRepository;
    private final AutoBankDetailsMapper autoBankDetailsMapper = AutoBankDetailsMapper.BANK_DETAILS_MAPPER;

    @Override
    @Transactional
    public BankDetailsDTO createBankDetails(BankDetailsDTO bankDetailsDTO) {
        log.info("Creating new bank details: {}", bankDetailsDTO);

        if (bankDetailsDTO == null) {
            log.error("Failed to create bank details: BankDetailsDTO is null");
            throw new IllegalArgumentException("BankDetailsDTO must not be null");
        }

        BankDetails bankDetails = autoBankDetailsMapper.mapToBankDetails(bankDetailsDTO);
        BankDetails savedBankDetails = bankDetailsRepository.save(bankDetails);

        log.info("Bank details successfully created: {}", savedBankDetails);
        return autoBankDetailsMapper.mapToBankDetailsDTO(savedBankDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public BankDetailsDTO getBankDetailsById(Long id) {
        log.info("Fetching bank details by ID: {}", id);

        if (id == null) {
            log.error("ID must not be null");
            throw new IllegalArgumentException("ID must not be null");
        }

        BankDetails bankDetails = bankDetailsRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Bank details not found for ID: {}", id);
                    return new ResourceNotFoundException("Bank details not found");
                });

        log.info("Bank details found: {}", bankDetails);
        return autoBankDetailsMapper.mapToBankDetailsDTO(bankDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankDetailsDTO> getAllBankDetails() {
        log.info("Fetching all bank details");

        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        log.info("Total bank details fetched: {}", bankDetailsList.size());

        return bankDetailsList.stream()
                .map(autoBankDetailsMapper::mapToBankDetailsDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BankDetailsDTO updateBankDetails(BankDetailsDTO bankDetailsDTO) {
        log.info("Updating bank details: {}", bankDetailsDTO);

        if (bankDetailsDTO == null) {
            log.error("Failed to update bank details: BankDetailsDTO is null");
            throw new IllegalArgumentException("BankDetailsDTO must not be null");
        }

        Long id = bankDetailsDTO.getId();
        if (id == null) {
            log.error("ID must not be null");
            throw new IllegalArgumentException("ID must not be null");
        }

        BankDetails existingBankDetail = bankDetailsRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Bank details not found for ID: {}", id);
                    return new ResourceNotFoundException("Bank details not found");
                });

        existingBankDetail.setBik(bankDetailsDTO.getBik());
        existingBankDetail.setInn(bankDetailsDTO.getInn());
        existingBankDetail.setKpp(bankDetailsDTO.getKpp());
        existingBankDetail.setCorAccount(bankDetailsDTO.getCorAccount());
        existingBankDetail.setCity(bankDetailsDTO.getCity());
        existingBankDetail.setJointStockCompany(bankDetailsDTO.getJointStockCompany());
        existingBankDetail.setName(bankDetailsDTO.getName());

        BankDetails updatedBankDetail = bankDetailsRepository.save(existingBankDetail);

        log.info("Bank details successfully updated: {}", updatedBankDetail);
        return autoBankDetailsMapper.mapToBankDetailsDTO(updatedBankDetail);
    }

    @Override
    @Transactional
    public void deleteBankDetails(Long id) {
        log.info("Deleting bank details with ID: {}", id);

        if (id == null) {
            log.error("ID must not be null");
            throw new IllegalArgumentException("ID must not be null");
        }

        if (!bankDetailsRepository.existsById(id)) {
            log.error("No bank details found with ID: {}", id);
            throw new ResourceNotFoundException("Bank details not found");
        }

        bankDetailsRepository.deleteById(id);
        log.info("Bank details with ID {} successfully deleted", id);
    }
}
package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.ATMDTO;
import com.bank.publicinfo.exception.ResourceNotFoundException;
import com.bank.publicinfo.mapper.AutoATMMapper;
import com.bank.publicinfo.model.ATM;
import com.bank.publicinfo.repositories.ATMRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ATMServiceImpl implements ATMService {

    private final ATMRepository atmRepository;
    private final AutoATMMapper autoATMMapper = AutoATMMapper.ATM_MAPPER;

    @Override
    @Transactional
    public ATMDTO createATM(ATMDTO atmDTO) {

        log.info("Creating a new ATM: {}", atmDTO);

        if (atmDTO == null) {
            log.error("Failed to create ATM: ATMDTO is null");
            throw new IllegalArgumentException("ATMDTO must not be null");
        }

        ATM atm = autoATMMapper.mapToATM(atmDTO);
        ATM savedAtm = atmRepository.save(atm);

        log.info("ATM successfully created: {}", savedAtm);

        return autoATMMapper.mapToATMDTO(savedAtm);
    }

    @Override
    @Transactional(readOnly = true)
    public ATMDTO getATMById(Long id) {

        log.info("Fetching ATM by ID: {}", id);

        if (id == null) {
            log.error("ID must not be null");
            throw new IllegalArgumentException("ID must not be null");
        }

        ATM atm = atmRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("ATM with ID {} not found", id);
                    return new ResourceNotFoundException("ATM not found");
                });

        log.info("ATM found: {}", atm);

        return autoATMMapper.mapToATMDTO(atm);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ATMDTO> getAllATMs() {

        log.info("Fetching all ATMs");

        List<ATM> atms = atmRepository.findAll();

        return atms.stream()
                .map(autoATMMapper::mapToATMDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ATMDTO updateATM(ATMDTO atmDTO) {

        log.info("Updating ATM: {}", atmDTO);

        if (atmDTO == null) {
            log.error("Failed to update ATM: ATMDTO is null");
            throw new IllegalArgumentException("ATMDTO must not be null");
        }

        Long id = atmDTO.getId();

        if (id == null) {
            log.error("ID must not be null");
            throw new IllegalArgumentException("ID must not be null");
        }

        ATM existingATM = atmRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("ATM with ID {} not found", id);
                    return new ResourceNotFoundException("ATM not found");
                });

        existingATM.setAddress(atmDTO.getAddress());
        existingATM.setStartOfWork(atmDTO.getStartOfWork());
        existingATM.setEndOfWork(atmDTO.getEndOfWork());
        existingATM.setAllHours(atmDTO.getAllHours());

        ATM updatedATM = atmRepository.save(existingATM);

        log.info("ATM successfully updated: {}", updatedATM);

        return autoATMMapper.mapToATMDTO(updatedATM);
    }

    @Override
    @Transactional
    public void deleteATM(Long id) {
        log.info("Deleting ATM with ID: {}", id);

        if (id == null) {
            log.error("ID must not be null");
            throw new IllegalArgumentException("ID must not be null");
        }

        if (!atmRepository.existsById(id)) {
            log.error("ATM with ID {} not found", id);
            throw new ResourceNotFoundException("ATM not found");
        }

        atmRepository.deleteById(id);
        log.info("ATM with ID {} successfully deleted", id);
    }

    @Override
    public boolean isATMAvailable(Long id) {
        log.info("Checking availability of ATM with ID: {}", id);

        ATM atm = atmRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("ATM with ID {} not found", id);
                    return new ResourceNotFoundException("ATM not found");
                });

        LocalTime currentTime = LocalTime.now();

        if (atm.getAllHours() != null && atm.getAllHours()) {
            log.info("The ATM operates 24/7.");
            return true;
        }

        if (atm.getStartOfWork() != null && atm.getEndOfWork() != null) {
            boolean isAvailable = !currentTime.isBefore(atm.getStartOfWork()) && !currentTime.isAfter(atm.getEndOfWork());
            log.info("Is the ATM available? {}", isAvailable);
            return isAvailable;
        }

        log.warn("Working hours are not specified; the ATM is considered unavailable.");
        return false;
    }
}
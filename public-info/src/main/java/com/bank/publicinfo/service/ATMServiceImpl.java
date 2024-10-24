package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.ATMDto;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ATMServiceImpl implements ATMService {

    private final ATMRepository atmRepository;
    private final AutoATMMapper autoATMMapper = AutoATMMapper.ATM_MAPPER;

    @Override
    @Transactional
    public ATMDto createATM(ATMDto atmDTO) {
        log.info("Creating a new ATM: {}", atmDTO);
        validateAtmDTO(atmDTO);

        ATM atm = autoATMMapper.mapToATM(atmDTO);
        ATM savedAtm = atmRepository.save(atm);

        log.info("ATM successfully created: {}", savedAtm);
        return autoATMMapper.mapToATMDto(savedAtm);
    }

    @Override
    @Transactional(readOnly = true)
    public ATMDto getATMById(Long id) {
        log.info("Fetching ATM by ID: {}", id);

        Long validatedId = Optional.ofNullable(id)
                .orElseThrow(() -> {
                    log.error("ID must not be null");
                    return new ResourceNotFoundException("ID must not be null");
                });

        ATM atm = atmRepository.findById(validatedId)
                .orElseThrow(() -> {
                    log.error("ATM with ID {} not found", validatedId);
                    return new ResourceNotFoundException("ATM not found");
                });

        log.info("ATM found: {}", atm);
        return autoATMMapper.mapToATMDto(atm);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ATMDto> getAllATMs() {
        log.info("Fetching all ATMs");

        List<ATM> atms = atmRepository.findAll();
        return atms.stream()
                .map(autoATMMapper::mapToATMDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ATMDto updateATM(ATMDto atmDTO) {
        log.info("Updating ATM: {}", atmDTO);
        validateAtmDTO(atmDTO);

        Long validatedId = Optional.ofNullable(atmDTO.getId())
                .orElseThrow(() -> {
                    log.error("ID must not be null");
                    return new ResourceNotFoundException("ID must not be null");
                });

        ATM existingATM = atmRepository.findById(validatedId)
                .orElseThrow(() -> {
                    log.error("ATM with ID {} not found", validatedId);
                    return new ResourceNotFoundException("ATM not found");
                });

        autoATMMapper.updateATMFromDto(atmDTO, existingATM);
        ATM updatedATM = atmRepository.save(existingATM);

        log.info("ATM successfully updated: {}", updatedATM);
        return autoATMMapper.mapToATMDto(updatedATM);
    }

    @Override
    @Transactional
    public void deleteATM(Long id) {
        log.info("Deleting ATM with ID: {}", id);

        Long validatedId = Optional.ofNullable(id)
                .orElseThrow(() -> {
                    log.error("ID must not be null");
                    return new ResourceNotFoundException("ID must not be null");
                });

        if (!atmRepository.existsById(validatedId)) {
            log.error("No ATM found with ID: {}", validatedId);
            throw new ResourceNotFoundException("ATM not found");
        }

        atmRepository.deleteById(validatedId);
        log.info("ATM with ID {} successfully deleted", validatedId);
    }

    @Override
    public boolean isATMAvailable(Long id) {
        log.info("Checking availability of ATM with ID: {}", id);

        Long validatedId = Optional.ofNullable(id)
                .orElseThrow(() -> {
                    log.error("ID must not be null");
                    return new ResourceNotFoundException("ID must not be null");
                });

        ATM atm = atmRepository.findById(validatedId)
                .orElseThrow(() -> {
                    log.error("ATM with ID {} not found", validatedId);
                    return new ResourceNotFoundException("ATM not found");
                });

        LocalTime currentTime = LocalTime.now();

        if (Boolean.TRUE.equals(atm.getAllHours())) {
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

    private void validateAtmDTO(ATMDto atmDTO) {
        if (atmDTO == null) {
            log.error("Failed to process ATM: ATMDTO is null");
            throw new ResourceNotFoundException("ATMDTO must not be null");
        }
    }
}
package com.bank.transfer.service;

import com.bank.transfer.dto.PhoneTransferDTO;
import com.bank.transfer.entity.PhoneTransferEntity;
import com.bank.transfer.exception.EntityNotFoundException;
import com.bank.transfer.exception.RequiredFieldEmptyException;
import com.bank.transfer.mapper.PhoneTransferMapper;
import com.bank.transfer.repository.PhoneTransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class PhoneTransferServiceImpl implements PhoneTransferService {

    private final PhoneTransferRepository phoneTransferRepository;

    private final PhoneTransferMapper phoneTransferMapper = PhoneTransferMapper.INSTANCE;

    @Autowired
    public PhoneTransferServiceImpl(PhoneTransferRepository phoneTransferRepository) {
        this.phoneTransferRepository = phoneTransferRepository;
    }

    @Override
    public List<PhoneTransferDTO> getAllPhoneTransfers() {

        log.info("Getting all phone transfers");

        List<PhoneTransferEntity> phoneTransfersListEntity = phoneTransferRepository.findAll();

        return phoneTransferMapper.toDTOList(phoneTransfersListEntity);
    }

    @Override
    public PhoneTransferDTO getPhoneTransferById(Long id) {

        log.info("Getting phone transfer by id: {}", id);

        PhoneTransferEntity phoneTransferEntity = phoneTransferRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("An object with this ID was not found, ID: " + id,
                                "PhoneTransferServiceImpl.getPhoneTransferById"));

        return phoneTransferMapper.toDTO(phoneTransferEntity);

    }

    @Transactional
    @Override
    public PhoneTransferDTO createPhoneTransfer(PhoneTransferDTO phoneTransferDTO) {

        log.info("Creating phone transfer: {}", phoneTransferDTO);

        if (phoneTransferDTO.getPhoneNumber() == null ||
                phoneTransferDTO.getAmount() == null ||
                phoneTransferDTO.getAccountDetailsId() == null) {

            throw new RequiredFieldEmptyException("An empty required field has been passed",
                    "CardTransferServiceImpl.createCardTransfer");
        }

        PhoneTransferEntity phoneTransferEntity = phoneTransferMapper.toEntity(phoneTransferDTO);
        PhoneTransferEntity savedPhoneTransferEntity = phoneTransferRepository.save(phoneTransferEntity);

        return phoneTransferMapper.toDTO(savedPhoneTransferEntity);
    }

    @Transactional
    @Override
    public PhoneTransferDTO updatePhoneTransfer(PhoneTransferDTO phoneTransferDTO, Long id) {

        log.info("Updating phone transfer: {}", phoneTransferDTO);

        PhoneTransferEntity existingPhoneTransferEntity = phoneTransferRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "An object with this ID was not found for editing, ID: " + id,
                                "PhoneTransferServiceImpl.updatePhoneTransfer"));

        phoneTransferMapper.updateEntityFromDTO(phoneTransferDTO, existingPhoneTransferEntity);

        PhoneTransferEntity savedPhoneTransferEntityEntity = phoneTransferRepository.save(existingPhoneTransferEntity);

        return phoneTransferMapper.toDTO(savedPhoneTransferEntityEntity);
    }

    @Override
    public PhoneTransferDTO deletePhoneTransfer(Long id) {

        log.info("Deleting phone transfer: {}", id);

        PhoneTransferEntity existsPhoneTransfer = phoneTransferRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "An object with this ID was not found for deletion, ID: " + id,
                        "PhoneTransferServiceImpl.deletePhoneTransfer"));

        phoneTransferRepository.deleteById(id);

        return phoneTransferMapper.toDTO(existsPhoneTransfer);
    }
}

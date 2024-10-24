package com.bank.antifraud.service;

import com.bank.antifraud.dto.PhoneTransfersDTO;
import com.bank.antifraud.exception.EntityNotFoundException;
import com.bank.antifraud.mapper.PhoneTransferMapper;
import com.bank.antifraud.model.SuspiciousPhoneTransfers;
import com.bank.antifraud.repository.SuspicionsPhoneTransfersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class SuspiciousPhoneTransfersServiceImp implements SuspiciousPhoneTransfersService {

    private final SuspicionsPhoneTransfersRepository sptRepository;
    private final PhoneTransferMapper phoneTransferMapper = PhoneTransferMapper.INSTANCE;

    @Autowired
    public SuspiciousPhoneTransfersServiceImp(SuspicionsPhoneTransfersRepository sptRepository) {
        this.sptRepository = sptRepository;

    }

    @Override
    public List<PhoneTransfersDTO> getListPhoneTransfers() {

        log.info("Getting all account transfers");
        List<SuspiciousPhoneTransfers> accountTransfersListEntity = sptRepository.findAll();

        return phoneTransferMapper.toDTOList(accountTransfersListEntity);
    }

    @Override
    @Transactional
    public PhoneTransfersDTO savePhone(PhoneTransfersDTO newPhoneTransfers) {


        log.info("Getting account transfer by id: {}", newPhoneTransfers);
        SuspiciousPhoneTransfers addPhone = phoneTransferMapper.toEntity(newPhoneTransfers);
        SuspiciousPhoneTransfers accountSave = sptRepository.save(addPhone);

        return phoneTransferMapper.toDTO(accountSave);

    }

    @Override
    @Transactional
    public PhoneTransfersDTO updatePhone(PhoneTransfersDTO updateTrDTO, Long id) {

        log.info("Creating account transfer: {}", updateTrDTO);
        SuspiciousPhoneTransfers phoneTransfers = sptRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AccountTransfers not found",
                        "SuspiciousPhoneTransfersServiceImp.update"));
        phoneTransferMapper.updateEntityFromDTO(updateTrDTO, phoneTransfers);
        SuspiciousPhoneTransfers updateAccount = sptRepository.save(phoneTransfers);

        return phoneTransferMapper.toDTO(updateAccount);
    }

    @Override
    public void delete(Long id) {

        log.info("Deleting account transfer: {}", id);
        try {
            sptRepository.deleteById(id);
        } catch (EntityNotFoundException ex) {

            throw new EntityNotFoundException(
                    "An object with this ID was not found for deletion, ID: " + id,
                    "AccountTransferServiceImpl.deleteAccountTransfer");
        }
    }

    @Override
    public PhoneTransfersDTO getPhoneTransfer(Long id) {

        log.info("Getting account transfer by id: {}", id);
        SuspiciousPhoneTransfers accountTransferEntity = sptRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("An object with this ID was not found, ID: " + id,
                                "AccountTransferServiceImpl.getAccountTransferById"));

        return phoneTransferMapper.toDTO(accountTransferEntity);
    }
}

package com.bank.antifraud.service;

import com.bank.antifraud.dto.PhoneTransfersDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SuspiciousPhoneTransfersService {

    List<PhoneTransfersDTO> getListPhoneTransfers();

    @Transactional
    PhoneTransfersDTO savePhone(PhoneTransfersDTO newPhoneTransfers);

    @Transactional
    PhoneTransfersDTO updatePhone(PhoneTransfersDTO updateTrDTO, Long id);

    void delete(Long id);

    PhoneTransfersDTO getPhoneTransfer(Long id);
}

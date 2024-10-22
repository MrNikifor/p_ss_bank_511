package com.bank.transfer.service;

import com.bank.transfer.dto.PhoneTransferDTO;

import java.util.List;

public interface PhoneTransferService {

    List<PhoneTransferDTO> getAllPhoneTransfers();

    PhoneTransferDTO getPhoneTransferById(Long id);

    PhoneTransferDTO createPhoneTransfer(PhoneTransferDTO phoneTransferDTO);

    PhoneTransferDTO updatePhoneTransfer(PhoneTransferDTO phoneTransferDTO, Long id);

    PhoneTransferDTO deletePhoneTransfer(Long id);
}

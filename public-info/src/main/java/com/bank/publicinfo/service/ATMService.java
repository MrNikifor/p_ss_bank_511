package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.ATMDTO;

import java.util.List;

public interface ATMService {
    ATMDTO createATM(ATMDTO atmDTO);
    ATMDTO getATMById(Long id);
    List<ATMDTO> getAllATMs();
    ATMDTO updateATM(ATMDTO atmDTO);
    void deleteATM(Long id);
    boolean isATMAvailable(Long id);
}

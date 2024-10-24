package com.bank.antifraud.service;

import com.bank.antifraud.model.AuditModel;

import java.util.List;

public interface AuditService {


    void createAudit(AuditModel newAudit);

    void updateAudit(AuditModel updateAudit);

    AuditModel findById(Long id);

    List<AuditModel> findAll();

    void deleteById(Long id);
}

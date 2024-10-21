package com.bank.account.service;

import com.bank.account.entity.Audit;

import java.util.List;

public interface AuditService {
    void createAudit(Audit audit);
    Audit getAudit(Long id);
    List<Audit> getAllAudits();
    Audit findAuditByJson(String json);
}

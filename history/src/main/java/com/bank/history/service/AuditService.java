package com.bank.history.service;

import com.bank.history.entity.Audit;

import java.util.List;

public interface AuditService {

    List<Audit> getAudits();

    Audit getAudit(long id);

    Audit findAuditByJson(String json);

    void createAudit(Audit audit);
}

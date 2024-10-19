package com.bank.publicinfo.service;

import com.bank.publicinfo.model.Audit;

import java.util.List;
import java.util.Optional;

public interface AuditService {
    void createAudit(Audit audit);
    Optional<Audit> findFirstByEntityJsonStartingWith(String json);
    Audit findById(Long id);
    List<Audit> findAll();
    void deleteById(Long id);
}

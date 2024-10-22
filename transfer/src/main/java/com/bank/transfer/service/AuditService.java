package com.bank.transfer.service;

import com.bank.transfer.entity.AuditEntity;

import java.util.List;

public interface AuditService {

    List<AuditEntity> getAllAudits();

    AuditEntity getAuditById(Long id);

    void createAudit(AuditEntity auditEntity);

    AuditEntity updateAudit(AuditEntity auditEntity, Long id);

    AuditEntity deleteAudit(Long id);

    AuditEntity getLatestAuditByJson(Long id, String entityType);
}

package com.bank.transfer.service;

import com.bank.transfer.annotation.NoAudit;
import com.bank.transfer.entity.AuditEntity;
import com.bank.transfer.exception.AuditRecordNotFoundException;
import com.bank.transfer.repository.AuditRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AuditServiceImpl implements AuditService {

    AuditRepository auditRepository;

    @Autowired
    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public AuditEntity getLatestAuditByJson(Long id, String entityType) {

        log.info("Get the latest Audit record from the entity_type: {} column" +
                " and the id {} from the json in the entity_json column", entityType, id);

        AuditEntity auditEntity = auditRepository.getLatestAuditByJson(id, entityType);

        if (auditEntity == null) {
            throw new AuditRecordNotFoundException("The data on the audit entry could not be found",
                    "AuditServiceImpl.getLatestAuditByJson");
        }

        return auditEntity;
    }

    @Override
    public List<AuditEntity> getAllAudits() {

        log.info("Get a list of all audit records");

        return auditRepository.findAll();
    }

    @Override
    public AuditEntity getAuditById(Long id) {

        log.info("Get the audit record from the id {}", id);

        return auditRepository.findById(id).orElseThrow(
                () -> new AuditRecordNotFoundException(
                        "The audit record with the specified ID was not found! ID: " + id,
                        "AuditServiceImpl.getAuditById"));
    }

    @Transactional
    @NoAudit
    @Override
    public void createAudit(AuditEntity auditEntity) {

        log.info("Create the audit record {}", auditEntity);

        if (auditEntity.getId() != null) {
            auditEntity.setId(null);
        }

        auditRepository.save(auditEntity);
    }

    @Transactional
    @Override
    public AuditEntity updateAudit(AuditEntity auditEntity, Long id) {

        log.info("Update the audit report with new data: {}", auditEntity);

        AuditEntity auditEntityFromDB = auditRepository.findById(id).orElseThrow(
                () -> new AuditRecordNotFoundException(
                        "The audit record with the specified ID was not found! ID: " + id,
                        "AuditServiceImpl.updateAudit"));

        auditEntityFromDB.setEntityType(auditEntity.getEntityType());
        auditEntityFromDB.setOperationType(auditEntity.getOperationType());
        auditEntityFromDB.setCreatedBy(auditEntity.getCreatedBy());
        auditEntityFromDB.setModifiedBy(auditEntity.getModifiedBy());
        auditEntityFromDB.setCreatedAt(auditEntity.getCreatedAt());
        auditEntityFromDB.setNewEntityJson(auditEntity.getNewEntityJson());
        auditEntityFromDB.setEntityJson(auditEntity.getEntityJson());
        auditEntityFromDB.setModifiedAt(LocalDateTime.now());

        return auditRepository.save(auditEntityFromDB);
    }

    @Transactional
    @Override
    public AuditEntity deleteAudit(Long id) {

        log.info("Delete the audit record {}", id);

        AuditEntity auditEntity = auditRepository.findById(id).orElseThrow(
                () -> new AuditRecordNotFoundException(
                        "The audit record with the specified ID was not found! ID: " + id,
                        "AuditServiceImpl.deleteAudit"));

        auditRepository.deleteById(id);

        return auditEntity;
    }
}

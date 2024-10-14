package com.bank.publicinfo.service;

import com.bank.publicinfo.exception.AuditNotFoundException;
import com.bank.publicinfo.model.Audit;
import com.bank.publicinfo.repositories.AuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    @Override
    public void createAudit(Audit audit) {
        log.info("Creating audit record: {}", audit);
        auditRepository.save(audit);
        log.info("Audit record created successfully with ID: {}", audit.getId());
    }

    @Override
    public Optional<Audit> findFirstByEntityJsonStartingWith(String json) {
        log.info("Finding first audit record starting with JSON: {}", json);
        Optional<Audit> audit = auditRepository.findFirstByEntityJsonStartingWithOrderByModifiedAt(json);
        if (audit.isPresent()) {
            log.info("Found audit record: {}", audit.get());
        } else {
            log.warn("No audit record found starting with JSON: {}", json);
        }
        return audit;
    }

    @Override
    public Optional<Audit> findById(Long id) {
        log.info("Finding audit record with ID: {}", id);
        Optional<Audit> audit = auditRepository.findById(id);

        if (audit.isPresent()) {
            log.info("Found audit record: {}", audit.get());
        } else {
            log.warn("Audit not found with ID: {}", id);
        }

        return audit; // Возвращаем Optional
    }

    @Override
    public List<Audit> findAll() {
        log.info("Retrieving all audit records");
        List<Audit> audits = auditRepository.findAll();
        log.info("Total audits retrieved: {}", audits.size());
        return audits;
    }

    @Override
    public void deleteById(Long id) {
        log.info("Attempting to delete audit record with ID: {}", id);
        if (!auditRepository.existsById(id)) {
            log.error("Cannot delete. Audit not found with ID: {}", id);
            throw new AuditNotFoundException("Cannot delete. Audit not found with ID: " + id);
        }
        auditRepository.deleteById(id);
        log.info("Successfully deleted audit record with ID: {}", id);
    }
}
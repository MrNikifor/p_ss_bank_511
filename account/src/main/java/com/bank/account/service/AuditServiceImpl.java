package com.bank.account.service;

import com.bank.account.entity.Audit;
import com.bank.account.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;

    @Override
    @Transactional
    public void createAudit(Audit audit) {
        try {
            auditRepository.save(audit);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Audit getAudit(Long id) {
        try {
            return auditRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Audit> getAllAudits() {
        try {
            return auditRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Audit findAuditByJson(String json) {
        try {
            return auditRepository.findFirstByEntityJsonStartingWithOrderByModifiedAt(json);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}

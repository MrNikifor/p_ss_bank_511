package com.bank.history.service;

import com.bank.history.entity.Audit;
import com.bank.history.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    @Override
    public List<Audit> getAudits() {
        try {
            return auditRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Audit getAudit(long id) {
        try {
            return auditRepository.findById(id).orElse(null);
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

    @Override
    public void createAudit(Audit audit) {
        try {
            auditRepository.save(audit);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}

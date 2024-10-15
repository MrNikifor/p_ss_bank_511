package com.bank.publicinfo.aspect;

import com.bank.publicinfo.model.Audit;
import com.bank.publicinfo.repositories.AuditRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {

    private final AuditRepository auditRepository;
    private final ObjectMapper objectMapper;


    @AfterReturning(pointcut = "execution(* com.bank.publicinfo.service.*.create*(..))", returning = "result")
    public void afterResultCreateAdvice(JoinPoint joinPoint, Object result) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Audit audit = new Audit();
        try {
            String entityJson = objectMapper.writeValueAsString(result);
            audit.setEntityType(result.getClass().getSimpleName());
            audit.setOperationType(methodSignature.getName());
            audit.setCreatedBy("Ivan Pereoridaroga");
            audit.setCreatedAt(LocalDateTime.now());
            audit.setEntityJson(entityJson);
            auditRepository.save(audit);

            log.info("Created audit record for entity: {}", result.getClass().getSimpleName());
        } catch (JsonProcessingException e) {
            log.error("Error auditing creation of record {}: {}", result, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @AfterReturning(pointcut = "execution(* com.bank.publicinfo.service.*.update*(..))", returning = "result")
    public void afterResultUpdateAdvice(JoinPoint joinPoint, Object result) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Audit audit = new Audit();
        try {
            String json = objectMapper.writeValueAsString(result);
            Optional<Audit> oldAudit = auditRepository.findFirstByEntityJsonStartingWithOrderByModifiedAt("{\"id\":" + objectMapper.readTree(json).get("id") + ",");
            if (oldAudit.isPresent()) {
                Audit oldAuditO = oldAudit.get();
                audit.setEntityType(result.getClass().getSimpleName());
                audit.setOperationType(methodSignature.getName());
                audit.setCreatedBy(oldAuditO.getCreatedBy());
                audit.setModifiedBy("Ivan Pereoridaroga");
                audit.setCreatedAt(oldAuditO.getCreatedAt());
                audit.setModifiedAt(LocalDateTime.now());
                audit.setNewEntityJson(json);
                audit.setEntityJson(oldAuditO.getEntityJson());
                auditRepository.save(audit);

                log.info("Updated audit record for entity: {}", result.getClass().getSimpleName());
            } else {
                log.warn("No previous audit record found for entity ID: {}", objectMapper.readTree(json).get("id"));
            }
        } catch (JsonProcessingException e) {
            log.error("Error auditing update of record {}: {}", result, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
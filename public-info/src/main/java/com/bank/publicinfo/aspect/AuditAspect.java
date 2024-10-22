package com.bank.publicinfo.aspect;

import com.bank.publicinfo.model.Audit;
import com.bank.publicinfo.service.AuditService;
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

    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    private static final String CREATED_BY = "Ivan Pereoridaroga";

    @AfterReturning(pointcut = "execution(* com.bank.publicinfo.service.*.create*(..))", returning = "result")
    public void afterResultCreateAdvice(JoinPoint joinPoint, Object result) {
        if (result == null) {
            log.warn("Result is null for method: {}", joinPoint.getSignature().getName());
            return;
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        try {
            String json = objectMapper.writeValueAsString(result);

            createAndSave(signature, CREATED_BY, null, LocalDateTime.now(), null, null, json);

            log.info("Created audit record for entity: {}", result.getClass().getSimpleName());

        } catch (JsonProcessingException e) {
            log.error("JSON processing error for method {}: {}", signature.getName(), e.getMessage());
            throw new RuntimeException("JSON processing error", e);
        }
    }

    @AfterReturning(pointcut = "execution(* com.bank.publicinfo.service.*.update*(..))", returning = "result")
    public void afterResultUpdateAdvice(JoinPoint joinPoint, Object result) {
        if (result == null) {
            log.warn("Result is null for method: {}", joinPoint.getSignature().getName());
            return;
        }

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        try {
            String json = objectMapper.writeValueAsString(result);
            Optional<Audit> oldAudit = auditService.findFirstByEntityJsonStartingWith("{\"id\":" + objectMapper.readTree(json).get("id") + ",");

            if (oldAudit.isPresent()) {
                createAndSave(methodSignature, oldAudit.get().getCreatedBy(), CREATED_BY, oldAudit.get().getCreatedAt(),
                        LocalDateTime.now(), json, oldAudit.get().getEntityJson());
                log.info("Updated audit record for entity: {}", result.getClass().getSimpleName());
            } else {
                log.warn("No previous audit record found for entity with ID: {}", objectMapper.readTree(json).get("id"));
            }
        } catch (JsonProcessingException e) {
            log.error("JSON processing error for method {}: {}", methodSignature.getName(), e.getMessage());
            throw new RuntimeException("JSON processing error", e);
        }
    }

    private void createAndSave(MethodSignature signature,
                               String createdBy,
                               String modifiedBy,
                               LocalDateTime createdAt,
                               LocalDateTime modifiedAt,
                               String newEntityJson,
                               String entityJson) {
        Audit audit = new Audit();
        audit.setEntityType(signature.getParameterTypes()[0].getSimpleName());
        audit.setOperationType(signature.getMethod().getName());
        audit.setCreatedBy(createdBy);
        audit.setModifiedBy(modifiedBy);
        audit.setCreatedAt(createdAt);
        audit.setModifiedAt(modifiedAt);
        audit.setNewEntityJson(newEntityJson);
        audit.setEntityJson(entityJson);

        auditService.createAudit(audit);
    }
}
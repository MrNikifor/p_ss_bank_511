package com.bank.history.aspect;


import com.bank.history.entity.Audit;
import com.bank.history.entity.History;
import com.bank.history.service.AuditService;
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

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {

    private final ObjectMapper objectMapper;
    private final AuditService auditService;

    @AfterReturning(pointcut = "execution(* com.bank.history.service.*.save*(..))", returning = "history")
    public void auditSave(JoinPoint joinPoint, History history) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        try {
            String json = objectMapper.writeValueAsString(history);
            createAndSave(signature, "admin", null, LocalDateTime.now(),
                    null, null, json);
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации объекта History", e);
        }
    }

    @AfterReturning(pointcut = "execution(* com.bank.history.service.*.update*(..))", returning = "history")
    public void auditUpdate(JoinPoint joinPoint, History history) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        try {
            String json = objectMapper.writeValueAsString(history);
            Audit oldAudit = auditService.findAuditByJson("{\"id\":" + objectMapper.readTree(json).get("id") + ",");
            createAndSave(signature, oldAudit.getCreatedBy(), "admin", oldAudit.getCreatedAt(),
                    LocalDateTime.now(), json, oldAudit.getEntityJson());
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации обЪекта History", e);
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

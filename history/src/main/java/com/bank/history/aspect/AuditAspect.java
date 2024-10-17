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
            Audit audit = new Audit();
            audit.setEntityType(signature.getParameterTypes()[0].getSimpleName());
            audit.setOperationType(signature.getMethod().getName());
            audit.setCreatedBy("admin");
            audit.setModifiedBy(null);
            audit.setCreatedAt(LocalDateTime.now());
            audit.setModifiedAt(null);
            audit.setNewEntityJson(null);
            audit.setEntityJson(json);
            auditService.createAudit(audit);
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации обЪекта History", e);
        }
    }

    @AfterReturning(pointcut = "execution(* com.bank.history.service.*.update*(..))", returning = "history")
    public void auditUpdate(JoinPoint joinPoint, History history) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        try {
            String json = objectMapper.writeValueAsString(history);
            Audit oldAudit = auditService.findAuditByJson("{\"id\":" + objectMapper.readTree(json).get("id") + ",");
            Audit audit = new Audit();
            audit.setEntityType(signature.getParameterTypes()[0].getSimpleName());
            audit.setOperationType(signature.getMethod().getName());
            audit.setCreatedBy(oldAudit.getCreatedBy());
            audit.setModifiedBy("admin");
            audit.setCreatedAt(oldAudit.getCreatedAt());
            audit.setModifiedAt(LocalDateTime.now());
            audit.setNewEntityJson(json);
            audit.setEntityJson(oldAudit.getEntityJson());
            auditService.createAudit(audit);
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации обЪекта History", e);
        }
    }
}

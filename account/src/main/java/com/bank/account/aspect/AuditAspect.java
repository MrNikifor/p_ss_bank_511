package com.bank.account.aspect;

import com.bank.account.entity.AccountDetails;
import com.bank.account.entity.Audit;
import com.bank.account.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {
    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    @AfterReturning(pointcut = "execution(* com.bank.account.service.*.save*(..))", returning = "accountDetails")
    public void auditSave(JoinPoint joinPoint, AccountDetails accountDetails) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        try {
            String json = objectMapper.writeValueAsString(accountDetails);
            Audit audit = new Audit();
            audit.setEntityType(signature.getParameterTypes()[0].getSimpleName());
            audit.setOperationType(signature.getMethod().getName());
            audit.setCreatedBy(objectMapper.readTree(json).get("profileId").asText());
            audit.setModifiedBy(null);
            audit.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            audit.setModifiedAt(null);
            audit.setNewEntityJson(null);
            audit.setEntityJson(json);
            auditService.createAudit(audit);
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации объекта AccountDetails", e);
        }
    }

    @AfterReturning(pointcut = "execution(* com.bank.account.service.*.update*(..))", returning = "accountDetails")
    public void auditUpdate(JoinPoint joinPoint, AccountDetails accountDetails) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        try {
            String json = objectMapper.writeValueAsString(accountDetails);
            Audit oldAudit = auditService.findAuditByJson("{\"id\":" + objectMapper.readTree(json).get("id") + ",");
            Audit audit = new Audit();
            audit.setEntityType(signature.getParameterTypes()[0].getSimpleName());
            audit.setOperationType(signature.getMethod().getName());
            audit.setCreatedBy(oldAudit.getCreatedBy());
            audit.setModifiedBy(objectMapper.readTree(json).get("profileId").asText());
            audit.setCreatedAt(oldAudit.getCreatedAt());
            audit.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
            audit.setNewEntityJson(json);
            audit.setEntityJson(oldAudit.getEntityJson());
            auditService.createAudit(audit);
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации объекта AccountDetails", e);
        }
    }
}

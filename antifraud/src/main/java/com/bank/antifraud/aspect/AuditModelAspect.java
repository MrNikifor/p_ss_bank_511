package com.bank.antifraud.aspect;

import com.bank.antifraud.model.AuditModel;
import com.bank.antifraud.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class AuditModelAspect {

    private final AuditService auditModelService;
    private final ObjectMapper objectMapper;

    public AuditModelAspect(AuditService auditModelService, ObjectMapper objectMapper) {
        this.auditModelService = auditModelService;
        this.objectMapper = objectMapper;
    }

    @AfterReturning(pointcut = "execution(* com.bank.antifraud.service.*.save*(..)) || execution(* com.bank.antifraud.service.*.update*(..))", returning = "result")
    public void afterResultAdvice(JoinPoint joinPoint, Object result) {
        if (result == null) {
            log.warn("Result is null for method: {}", joinPoint.getSignature().getName());
            return;
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AuditModel auditModel = new AuditModel();
        try {
            String entityJson = objectMapper.writeValueAsString(result);
            auditModel.setEntityType(result.getClass().getSimpleName());
            auditModel.setOperationType(methodSignature.getName());
            auditModel.setCreatedBy("Максим");
            auditModel.setCreatedAt(LocalDateTime.now());
            auditModel.setEntityJson(entityJson);
            auditModelService.createAudit(auditModel);

            log.info("Created auditModel record for entity: {}", result.getClass().getSimpleName());
        } catch (JsonProcessingException e) {
            log.error("Error auditModeling creation of record {}: {}", result, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
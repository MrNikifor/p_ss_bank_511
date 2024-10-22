package com.bank.transfer.aop.aspects;

import com.bank.transfer.annotation.EntityType;
import com.bank.transfer.dto.Identifiable;
import com.bank.transfer.entity.AuditEntity;
import com.bank.transfer.exception.EntityIdNotFoundException;
import com.bank.transfer.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AuditAspect {

    private final AuditService auditService;

    private final ObjectMapper objectMapper;

    private ThreadLocal<AuditEntity> auditThreadLocal = new ThreadLocal<>();

    @Autowired
    public AuditAspect(AuditService auditService, ObjectMapper objectMapper) {
        this.auditService = auditService;
        this.objectMapper = objectMapper;
    }

    @Before("execution(* com.bank.transfer.service.*.create*(..)) && !@annotation(com.bank.transfer.annotation.NoAudit)")
    public void beforeCreateMethodExecution(JoinPoint joinPoint) {

        beforeMethodExecutionWorkingWithEntity(joinPoint, "CREATE", "test", null);
    }

    @Before("execution(* com.bank.transfer.service.*.update*(..)) && !@annotation(com.bank.transfer.annotation.NoAudit)")
    public void beforeUpdateMethodExecution(JoinPoint joinPoint) {

        beforeMethodExecutionWorkingWithEntity(joinPoint, "UPDATE", null, "test1");
    }

    @AfterReturning(
            pointcut = "execution(* com.bank.transfer.service.*.create*(..)) && !@annotation(com.bank.transfer.annotation.NoAudit)",
            returning = "result")
    public void afterCreateMethodExecution(JoinPoint joinPoint, Object result) {

        log.info("after create method execution: {}", joinPoint.getSignature().getName());

        AuditEntity auditEntity = auditThreadLocal.get();

        if (auditEntity != null) {

            try {
                auditEntity.setEntityJson(objectMapper.writeValueAsString(result));
                auditEntity.setCreatedAt(LocalDateTime.now());

                auditService.createAudit(auditEntity);

            } catch (JsonProcessingException e) {

                log.error("Failed to serialize result into EntityJson", e);
                throw new RuntimeException(e);

            } finally {
                auditThreadLocal.remove();
            }
        }
    }

    @AfterReturning(
            pointcut = "execution(* com.bank.transfer.service.*.update*(..)) && !@annotation(com.bank.transfer.annotation.NoAudit)",
            returning = "result")
    public void afterUpdateMethodExecution(JoinPoint joinPoint, Object result) {

        log.info("after update method execution: {}", joinPoint.getSignature().getName());

        AuditEntity auditEntity = auditThreadLocal.get();

        if (auditEntity != null) {
            String entityType = getEntityType(joinPoint);
            Long entityID = getEntityId(result);

            AuditEntity lastEntryAudit = auditService.getLatestAuditByJson(entityID, entityType);

            auditEntity.setEntityType(entityType);
            auditEntity.setCreatedAt(lastEntryAudit.getCreatedAt());
            auditEntity.setCreatedBy(lastEntryAudit.getCreatedBy());
            auditEntity.setEntityJson(lastEntryAudit.getEntityJson());

            try {

                auditEntity.setNewEntityJson(objectMapper.writeValueAsString(result));
                auditEntity.setModifiedAt(LocalDateTime.now());

                auditService.createAudit(auditEntity);

            } catch (JsonProcessingException e) {

                log.error("Failed to serialize result into NewEntityJson", e);
                throw new RuntimeException(e);

            } finally {
                auditThreadLocal.remove();
            }
        }
    }

    @PreDestroy
    public void destroy() {
        auditThreadLocal.remove();

        log.info("AuditAspect destroy completed");
    }

    private void beforeMethodExecutionWorkingWithEntity(
            JoinPoint joinPoint, String operationType, String createdBy, String modifiedBy) {

        log.info("before {} method execution: {}", operationType.toLowerCase(), joinPoint.getSignature().getName());

        String entityType = getEntityType(joinPoint);

        auditThreadLocal.set(AuditEntity.builder()
                .entityType(entityType)
                .operationType(operationType)
                .createdBy(createdBy)
                .modifiedBy(modifiedBy)
                .modifiedAt(null)
                .newEntityJson(null)
                .build());
    }

    private String getEntityType(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class<?> parameterType = signature.getParameterTypes()[0];

        if (parameterType.isAnnotationPresent(EntityType.class)) {
            EntityType entityTypeAnnotation = parameterType.getAnnotation(EntityType.class);

            return entityTypeAnnotation.value();
        }

        return parameterType.getSimpleName();
    }

    private Long getEntityId(Object entity) {

        if (entity instanceof Identifiable) {
            return ((Identifiable) entity).getId();
        }

        throw new EntityIdNotFoundException("Entity does not have an ID field",
                "AuditAspect.afterCreateMethodExecution.getEntityId");
    }
}

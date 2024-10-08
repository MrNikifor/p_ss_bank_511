package com.bank.profile.aspect;

import com.bank.profile.entity.Audit;
import com.bank.profile.repository.AuditRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;


@Component
@Aspect
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditRepository auditRepository;
    private final ObjectMapper objectMapper;


    @AfterReturning(pointcut = "com.bank.profile.aspect.pointcuts.AuditPointcuts.createMethod()", returning = "result")
    public void afterResultCreateAdvice(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Audit audit = new Audit();
        try {
            String entityJson = objectMapper.writeValueAsString(result);
            audit.setEntityType(result.getClass().getSimpleName());
            audit.setOperationType(methodSignature.getName());
            audit.setCreatedBy("Tony Montana");
            audit.setCreatedAt(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
            audit.setEntityJson(entityJson);
            auditRepository.save(audit);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterReturning(pointcut = "com.bank.profile.aspect.pointcuts.AuditPointcuts.updateMethod()", returning = "result")
    public void afterResultUpdateAdvice(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        try {
            String json = objectMapper.writeValueAsString(result);
            Optional<Audit> oldAudit = auditRepository.findFirstByEntityJsonStartingWithOrderByModifiedAt("{\"id\":" + objectMapper.readTree(json).get("id") + ",");
            if (oldAudit.isPresent()) {
                Audit audit = new Audit();
                Audit oldAuditO = oldAudit.get();
                audit.setEntityType(result.getClass().getSimpleName());
                audit.setOperationType(methodSignature.getName());
                audit.setCreatedBy(oldAuditO.getCreatedBy());
                audit.setModifiedBy("Tony Montana");
                audit.setCreatedAt(oldAuditO.getCreatedAt());
                audit.setModifiedAt(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
                audit.setNewEntityJson(json);
                audit.setEntityJson(oldAuditO.getEntityJson());
                auditRepository.save(audit);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

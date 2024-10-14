package com.bank.publicinfo.aspect;

import com.bank.publicinfo.model.Audit;
import com.bank.publicinfo.repositories.AuditRepository;
import com.bank.publicinfo.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
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

    // ThreadLocal для хранения информации об аудите
    private ThreadLocal<Audit> auditThreadLocal = new ThreadLocal<>();

    @AfterReturning(pointcut = "execution(* com.bank.publicinfo.service.*.create*(..))", returning = "result")
    public void afterResultCreateAdvice(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Audit audit = new Audit();
        try {
            String entityJson = objectMapper.writeValueAsString(result);
            audit.setEntityType(result.getClass().getSimpleName());
            audit.setOperationType(methodSignature.getName());
            audit.setCreatedBy("Tony Montana");
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
                audit.setModifiedBy("Tony Montana");
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
package com.bank.profile.aspect;

import com.bank.profile.entity.Audit;
import com.bank.profile.repository.AuditRepository;
import com.bank.profile.service.generics.AbstractBaseCrudService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Aspect
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditRepository auditRepository;

    @AfterReturning(pointcut = "com.bank.profile.aspect.pointcuts.AuditPointcuts.createOrUpdateMethods()", returning = "result")
    public void beforeCreateOrUpdateAdvice(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        Object service = joinPoint.getThis();
        Audit audit = new Audit();

        AbstractBaseCrudService<?, ?, ?, ?> abstractBaseCrudService = (AbstractBaseCrudService<?, ?, ?, ?>) service;
        Class<?> entityType = abstractBaseCrudService.getEntityType();

        audit.setEntityType(entityType.getSimpleName());
        audit.setOperationType(methodSignature.getName());
        audit.setCreatedBy("Matvey"); //???
        audit.setCreatedAt(LocalDate.now());
        audit.setEntityJson(args[0].toString());
        audit.setNewEntityJson(result.toString());

        if (methodSignature.getName().equals("update")) {
            audit.setModifiedBy("Matvey"); //???
            audit.setModifiedAt(LocalDate.now());
        }
        auditRepository.save(audit);
    }

    @Before("com.bank.profile.aspect.pointcuts.AuditPointcuts.deleteMethod()")
    public void beforeDeleteAdvice(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        Object service = joinPoint.getThis();
        Audit audit = new Audit();

        AbstractBaseCrudService<?, ?, ?, ?> abstractBaseCrudService = (AbstractBaseCrudService<?, ?, ?, ?>) service;
        Class<?> entityType = abstractBaseCrudService.getEntityType();
        audit.setEntityType(entityType.getSimpleName());

        Object entity = abstractBaseCrudService.read((Long) args[0]);
        audit.setEntityJson(entity.toString());

        audit.setOperationType(methodSignature.getName());
        audit.setCreatedBy("Matvey"); //?????
        audit.setCreatedAt(LocalDate.now());

        auditRepository.save(audit);
    }


}

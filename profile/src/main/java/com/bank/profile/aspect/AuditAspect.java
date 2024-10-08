package com.bank.profile.aspect;

import com.bank.profile.entity.Audit;
import com.bank.profile.repository.AuditRepository;
import com.bank.profile.service.generics.AbstractBaseCrudService;
import io.micrometer.core.instrument.Clock;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.validation.ClockProvider;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Component
@Aspect
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditRepository auditRepository;


    @AfterReturning(pointcut = "com.bank.profile.aspect.pointcuts.AuditPointcuts.createMethod()", returning = "result")
    public void afterResultCreateOrUpdateAdvice(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        Object service = joinPoint.getThis();
        Audit audit = new Audit();

        audit.setEntityType(result.getClass().getSimpleName());
        audit.setOperationType(methodSignature.getName());
        audit.setCreatedBy("Matvey");
        audit.setCreatedAt(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        audit.setEntityJson(result.toString());
        audit.setNewEntityJson(result.toString());


        if (methodSignature.getName().equals("update")) {
            audit.setModifiedBy("Matvey");
            audit.setModifiedAt(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
            audit.setNewEntityJson(result.toString());
        }
        //auditRepository.save(audit);
    }

    @AfterReturning(pointcut = "com.bank.profile.aspect.pointcuts.AuditPointcuts.deleteMethod()", returning = "result")
    public void afterResultDeleteAdvice(JoinPoint joinPoint, Object result) {
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        Object[] args = joinPoint.getArgs();
//        Object service = joinPoint.getThis();
//        Audit audit = new Audit();
//
//        audit.setEntityType(result.getClass().getSimpleName());
//        audit.setOperationType(methodSignature.getName());
//        audit.setCreatedBy("Matvey");
//        audit.setCreatedAt(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
//        audit.setEntityJson(result.toString());

        //auditRepository.save(audit);
    }


}

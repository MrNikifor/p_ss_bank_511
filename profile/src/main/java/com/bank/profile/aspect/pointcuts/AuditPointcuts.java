package com.bank.profile.aspect.pointcuts;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
public class AuditPointcuts {

    @Pointcut("execution(* create(*)) || execution(* update(*))")
    public void createOrUpdateMethods(){}

    @Pointcut("execution(* delete(*))")
    public void deleteMethod(){}
}

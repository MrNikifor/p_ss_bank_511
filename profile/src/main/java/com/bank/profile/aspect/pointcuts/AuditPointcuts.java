package com.bank.profile.aspect.pointcuts;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
public class AuditPointcuts {

    @Pointcut("execution(* create(*))")
    public void createMethod(){}

    @Pointcut("execution(* update(*))")
    public void updateMethod(){}

    @Pointcut("execution(* delete(*))")
    public void deleteMethod(){}
}

package com.bank.account.controller.auditController;

import com.bank.account.entity.Audit;
import com.bank.account.service.AuditService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest")
public class AuditRestController {
    private final AuditService auditService;

    @Hidden
    @GetMapping("/audit")
    public List<Audit> getAllAudits() {
        return auditService.getAllAudits();
    }

    @Hidden
    @GetMapping("/audit/{id}")
    public Audit getAuditById(@PathVariable Long id) {
        return auditService.getAudit(id);
    }
}

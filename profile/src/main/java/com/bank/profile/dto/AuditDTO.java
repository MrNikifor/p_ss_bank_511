package com.bank.profile.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AuditDTO {
    private String entityType;
    private String operationType;
    private String createdBy;
    private String modifiedBy;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
    private String newEntityJson;
    private String entityJson;
}

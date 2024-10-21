package com.bank.profile.dto;

import com.bank.profile.dto.abstracts.AbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Component
@EqualsAndHashCode(callSuper = true)
public class AuditDTO extends AbstractDTO {
    private Long id;
    private String entityType;
    private String operationType;
    private String createdBy;
    private String modifiedBy;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
    private String newEntityJson;
    private String entityJson;
}

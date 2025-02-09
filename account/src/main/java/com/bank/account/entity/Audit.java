package com.bank.account.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "audit", schema = "account")
@Data
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "entity_type", length = 40, nullable = false)
    private String entityType;
    @Column(name = "operation_type", nullable = false)
    private String operationType;//
    @Column(name = "created_by", nullable = false)
    private String createdBy;
    @Column(name = "modified_by")
    private String modifiedBy;//
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @Column(name = "modified_at")
    private Timestamp modifiedAt;//
    @Column(name = "new_entity_json", columnDefinition = "TEXT")
    private String newEntityJson;//
    @Column(name = "entity_json", columnDefinition = "TEXT", nullable = false)
    private String entityJson;
}

package com.bank.publicinfo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "audit")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // уникальный идентификатор

    @Size(max = 40)
    @Column(name = "entity_type", nullable = false)
    private String entityType; // тип сущности

    @Column(name = "operation_type", nullable = false)
    private String operationType; // тип операции

    @CreatedBy
    @Size(max = 255)
    @Column(name = "created_by", nullable = false)
    private String createdBy; // кто создал запись

    @LastModifiedBy
    @Size(max = 255)
    @Column(name = "modified_by")
    private String modifiedBy; // кто изменил запись

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // время создания

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt; // время последнего изменения

    @Column(name = "new_entity_json", columnDefinition = "TEXT")
    private String newEntityJson; // новое состояние сущности в формате JSON

    @Column(name = "entity_json", columnDefinition = "TEXT", nullable = false)
    private String entityJson; // предыдущее состояние сущности в формате JSON
}

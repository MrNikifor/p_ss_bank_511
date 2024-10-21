package com.bank.profile.entity;

import com.bank.profile.entity.abstracts.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit", schema = "profile")
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
public class Audit extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity_type")
    @Size(max = 40)
    private String entityType;

    @Column(name = "operation_type")
    @Size(max = 255)
    private String operationType;

    @Column(name = "created_by")
    @Size(max = 255)
    private String createdBy;

    @Column(name = "modified_by", nullable = true)
    @Size(max = 255)
    private String modifiedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = true)
    private LocalDateTime modifiedAt;

    @Column(name = "new_entity_json", nullable = true)
    private String newEntityJson;

    @Column(name = "entity_json")
    private String entityJson;

}

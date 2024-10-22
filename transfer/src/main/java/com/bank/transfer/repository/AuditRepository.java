package com.bank.transfer.repository;

import com.bank.transfer.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuditRepository extends JpaRepository<AuditEntity, Long> {

    @Query(value = "SELECT * FROM transfer.audit " +
            "WHERE CAST(jsonb_extract_path_text(CAST(entity_json AS jsonb), 'id') AS INTEGER) = :id " +
            "AND entity_type = :entityType " +
            "ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    AuditEntity getLatestAuditByJson(@Param("id") Long id, @Param("entityType") String entityType);

}

package com.bank.antifraud.repository;

import com.bank.antifraud.model.AuditModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuditRepository extends JpaRepository<AuditModel, Long> {

    Optional<AuditModel> findFirstByEntityJsonStartingWithOrderByModifiedAt(String json);
}

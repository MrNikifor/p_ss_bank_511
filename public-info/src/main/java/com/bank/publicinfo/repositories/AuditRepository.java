package com.bank.publicinfo.repositories;

import com.bank.publicinfo.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
    Optional<Audit> findFirstByEntityJsonStartingWithOrderByModifiedAt(String json);
}

package com.bank.history.repository;

import com.bank.history.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

    Audit findFirstByEntityJsonStartingWithOrderByModifiedAt(String json);
}

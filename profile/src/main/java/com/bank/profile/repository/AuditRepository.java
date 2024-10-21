package com.bank.profile.repository;

import com.bank.profile.entity.Audit;
import com.bank.profile.repository.generics.BaseRepository;

import java.util.Optional;

public interface AuditRepository extends BaseRepository<Audit> {
    Optional<Audit> findFirstByEntityJsonStartingWithOrderByModifiedAt(String json);
}

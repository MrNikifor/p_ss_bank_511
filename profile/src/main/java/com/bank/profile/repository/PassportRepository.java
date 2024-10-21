package com.bank.profile.repository;

import com.bank.profile.entity.Passport;
import com.bank.profile.repository.generics.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportRepository extends BaseRepository<Passport> {
}

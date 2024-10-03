package com.bank.profile.repository.exact;

import com.bank.profile.entity.Passport;
import com.bank.profile.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportRepository extends BaseRepository<Passport, Long> {
}

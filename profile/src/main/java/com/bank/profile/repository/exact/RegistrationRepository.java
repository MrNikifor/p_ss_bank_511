package com.bank.profile.repository.exact;

import com.bank.profile.entity.Registration;
import com.bank.profile.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends BaseRepository<Registration, Long> {
}

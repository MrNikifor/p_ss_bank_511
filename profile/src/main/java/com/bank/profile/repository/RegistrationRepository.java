package com.bank.profile.repository;

import com.bank.profile.entity.exact.Registration;
import com.bank.profile.repository.generics.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends BaseRepository<Registration> {
}

package com.bank.profile.repository;

import com.bank.profile.entity.Profile;
import com.bank.profile.repository.generics.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends BaseRepository<Profile> {
}

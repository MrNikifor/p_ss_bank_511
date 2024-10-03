package com.bank.profile.repository.exact;

import com.bank.profile.entity.Profile;
import com.bank.profile.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends BaseRepository<Profile, Long> {
}

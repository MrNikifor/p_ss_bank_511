package com.bank.profile.repository.generics;

import com.bank.profile.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository <ENTITY extends AbstractEntity> extends JpaRepository<ENTITY, Long> {
}

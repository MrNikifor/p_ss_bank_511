package com.bank.transfer.repository;

import com.bank.transfer.entity.PhoneTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneTransferRepository extends JpaRepository<PhoneTransferEntity, Long> {
}

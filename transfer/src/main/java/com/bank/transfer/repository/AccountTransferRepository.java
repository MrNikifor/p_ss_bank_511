package com.bank.transfer.repository;

import com.bank.transfer.entity.AccountTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTransferRepository extends JpaRepository<AccountTransferEntity, Long> {

    Optional<AccountTransferEntity> findByAccountNumber(Long accountNumber);

}

package com.bank.transfer.repository;

import com.bank.transfer.entity.CardTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardTransferRepository extends JpaRepository<CardTransferEntity, Long> {

    Optional<CardTransferEntity> findByCardNumber(Long cardNumber);

}

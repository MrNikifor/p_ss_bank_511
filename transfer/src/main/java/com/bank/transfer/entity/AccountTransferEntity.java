package com.bank.transfer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Класс AccountTransferEntity представляет собой сущность для перевода средств.
 *
 * @since 1.0
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_transfer", schema = "transfer")
public class AccountTransferEntity {

    /**
     * Уникальный идентификатор перевода средств.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Уникальный номер счета, с которого осуществляется перевод.
     */
    @Column(name = "account_number", nullable = false, unique = true)
    private Long accountNumber;

    /**
     * Сумма перевода.
     */
    @Column(name = "amount", precision = 20, scale = 2, nullable = false)
    private BigDecimal amount;

    /**
     * Цель перевода.
     */
    @Column(name = "purpose", columnDefinition = "text")
    private String purpose;

    /**
     * Идентификатор деталей счета.
     */
    @Column(name = "account_details_id", nullable = false)
    private Long accountDetailsId;
}

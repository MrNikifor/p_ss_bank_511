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

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "card_transfer", schema = "transfer")
public class CardTransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number", nullable = false, unique = true)
    private Long cardNumber;

    @Column(name = "amount", precision = 20, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "purpose", columnDefinition = "text")
    private String purpose;

    @Column(name = "account_details_id", nullable = false)
    private Long accountDetailsId;
}

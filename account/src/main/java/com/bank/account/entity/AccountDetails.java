package com.bank.account.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "account_details", schema = "account")
@Data
public class AccountDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "passport_id", nullable = false)
    private Long passportId;
    @Column(name = "account_number", unique = true, nullable = false)
    private Long accountNumber;
    @Column(name = "bank_details_id", unique = true, nullable = false)
    private Long bankDetailsId;
    @Column(name = "money", precision = 20, scale = 2, nullable = false)
    private BigDecimal money;
    @Column(name = "negative_balance", nullable = false)
    private boolean negativeBalance;
    @Column(name = "profile_id", nullable = false)
    private Long profileId;
}

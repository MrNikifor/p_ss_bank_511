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

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "phone_transfer", schema = "transfer")
public class PhoneTransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", nullable = false)
    private Long phoneNumber;

    @Column(name = "amount", precision = 20, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "purpose", columnDefinition = "text")
    private String purpose;

    @Column(name = "account_details_id", nullable = false)
    private Long accountDetailsId;
}

package com.bank.antifraud.model;

import io.micrometer.core.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@NotNull
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "suspicious_account_phone", schema = "anti_fraud")
public class SuspiciousPhoneTransfers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "phone_transfer_id", unique = true)
    private Long phone_transfer_id;
    private boolean isBlocked;
    private boolean isSuspicious;
    @Nullable
    private String blockedReason;
    private String suspiciousReason;
}

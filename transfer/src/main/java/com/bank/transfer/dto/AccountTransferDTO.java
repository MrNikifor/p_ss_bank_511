package com.bank.transfer.dto;

import com.bank.transfer.annotation.EntityType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@EntityType("AccountTransfer")
public class AccountTransferDTO implements Identifiable {

    Long id;

    Long accountNumber;

    BigDecimal amount;

    String purpose;

    Long accountDetailsId;
}

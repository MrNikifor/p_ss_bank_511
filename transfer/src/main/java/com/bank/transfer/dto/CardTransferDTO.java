package com.bank.transfer.dto;

import com.bank.transfer.annotation.EntityType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@EntityType("CardTransfer")
public class CardTransferDTO implements Identifiable {

    Long id;

    Long cardNumber;

    BigDecimal amount;

    String purpose;

    Long accountDetailsId;
}

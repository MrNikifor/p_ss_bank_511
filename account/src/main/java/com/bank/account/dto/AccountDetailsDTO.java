package com.bank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Schema(description = "Счёт")
public class AccountDetailsDTO {
    private Long id;

    @Schema(description = "ID паспорта", example = "123456")
    @NotNull(message = "Паспорт ID не может быть пустым")
    @Min(value = 1, message = "Паспорт ID должен быть больше 0")
    private Long passportId;

    @Schema(description = "Номер счёта", example = "1234567")
    @NotNull(message = "Номер аккаунта не может быть пустым")
    @Min(value = 1, message = "Номер аккаунта должен быть больше 0")
    private Long accountNumber;

    @Schema(description = "ID банка", example = "12345678")
    @NotNull(message = "Поле \"Bank details ID\" не может быть пустым")
    @Min(value = 1, message = "Поле \"Bank details ID\" должно быть заполнено значением больше 0")
    private Long bankDetailsId;

    @Schema(description = "Средства на счету", example = "1000.59")
    @NotNull(message = "Поле \"money\" не может быть пустым")
    @Min(value = 0, message = "Поле \"money\" должно быть заполнено значением больше или равно 0")
    private BigDecimal money;

    @Schema(description = "Наличие задолженности", example = "false")
    private boolean negativeBalance;

    @Schema(description = "ID профиля вносящего изменения", example = "1")
    @NotNull(message = "ID профиля не может быть пустым")
    @Min(value = 1, message = "ID профиля должно быть больше 0")
    private Long profileId;
}
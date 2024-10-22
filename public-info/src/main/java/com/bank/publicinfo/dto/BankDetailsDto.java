package com.bank.publicinfo.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDetailsDto {

    private Long id;

    private Long bik;

    private Long inn;

    private Long kpp;

    private int corAccount;

    @Size(max = 180, message = "City name must be less than 180 characters")
    private String city;

    @NotNull(message = "Joint stock company must not be null")
    @Size(max = 15, message = "Joint stock company name must be less than 15 characters")
    private String jointStockCompany;

    @Size(max = 80, message = "Bank name is required and must be less than 80 characters")
    private String name;

    private Set<LicenseDto> licenses;

    private Set<CertificateDto> certificates;
}

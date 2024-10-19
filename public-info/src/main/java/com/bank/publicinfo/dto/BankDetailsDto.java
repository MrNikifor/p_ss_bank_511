package com.bank.publicinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankDetailsDto {

    private Long id;

    private long bik;

    private long inn;

    private long kpp;

    private int corAccount;

    @Size(max = 180, message = "City name must be less than 180 characters")
    private String city;

    @Size(max = 15, message = "Joint stock company name must be less than 15 characters")
    private String jointStockCompany;

    @Size(max = 80, message = "Bank name is required and must be less than 80 characters")
    private String name;

    private Set<LicenseDto> licenses;

    private Set<CertificateDto> certificates;
}

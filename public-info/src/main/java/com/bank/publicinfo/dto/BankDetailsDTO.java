package com.bank.publicinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDetailsDTO {
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

    private Set<LicenseDTO> licenses;

    private Set<CertificateDTO> certificates;
}

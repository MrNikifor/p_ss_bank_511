package com.bank.publicinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BranchDTO {
    private Long id;

    @Size(max = 370, message = "Address must be less than or equal to 370 characters")
    private String address;

    private Long phoneNumber;

    @Size(max = 250, message = "City name must be less than or equal to 100 characters")
    private String city;

    private LocalTime startOfWork;

    private LocalTime endOfWork;

    private Set<ATMDTO> atms;
}

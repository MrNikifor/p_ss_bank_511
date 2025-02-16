package com.bank.publicinfo.dto;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {
    private Long id;

    @Size(max = 370, message = "Address must be less than or equal to 370 characters")
    private String address;

    private Long phoneNumber;

    @Size(max = 250, message = "City name must be less than or equal to 100 characters")
    private String city;

    private LocalTime startOfWork;

    private LocalTime endOfWork;

    private Set<ATMDto> atms;
}

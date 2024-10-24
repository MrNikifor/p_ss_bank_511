package com.bank.publicinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ATMDto {
    private Long id;

    @Size(max = 370, message = "Address must be less than or equal to 370 characters")
    private String address;

    private LocalTime startOfWork;

    private LocalTime endOfWork;

    private Boolean allHours;

    private Long branchId;
}

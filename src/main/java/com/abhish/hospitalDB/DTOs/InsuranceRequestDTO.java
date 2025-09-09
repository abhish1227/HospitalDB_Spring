package com.abhish.hospitalDB.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InsuranceRequestDTO {

    @NotNull
    private String policyNumber;

    private String provider;

    @NotNull
    private LocalDate validUntil;
}

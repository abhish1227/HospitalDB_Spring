package com.abhish.hospitalDB.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InsuranceResponseDTO {

    private Long id;
    private String policyNumber;
    private String provider;
    private LocalDate validUntil;

}

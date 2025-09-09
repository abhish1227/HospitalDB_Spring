package com.abhish.hospitalDB.DTOs;

import com.abhish.hospitalDB.entity.enums.BloodGroup;
import com.abhish.hospitalDB.entity.enums.Gender;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PatientResponseDTO {

    private Long id;
    private String name;

    @Email
    private String email;

    private Gender gender;

    private LocalDate birthDate;

    private BloodGroup bloodGroup;

    private LocalDateTime createdAt;

    private InsuranceResponseDTO insurance;
}

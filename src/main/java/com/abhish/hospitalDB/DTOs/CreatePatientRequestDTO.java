package com.abhish.hospitalDB.DTOs;

import com.abhish.hospitalDB.entity.enums.BloodGroup;
import com.abhish.hospitalDB.entity.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreatePatientRequestDTO {

    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;

    private LocalDate birthDate;

    @NotNull
    private Gender gender;

    @NotNull(message = "Blood Group is required.")
    private BloodGroup bloodGroup;

}

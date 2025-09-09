package com.abhish.hospitalDB.DTOs;


import com.abhish.hospitalDB.entity.enums.BloodGroup;
import com.abhish.hospitalDB.entity.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



import java.time.LocalDate;

@Data
public class UpdatePatientRequestDTO {

    @NotNull
    private String name;

    private LocalDate birthDate;

    private Gender gender;

    private BloodGroup bloodGroup;
}

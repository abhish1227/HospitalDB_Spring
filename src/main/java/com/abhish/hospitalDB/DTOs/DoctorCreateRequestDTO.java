package com.abhish.hospitalDB.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorCreateRequestDTO {

    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;

    @NotNull(message = "Specialization is a required field.")
    private String specialization;

}

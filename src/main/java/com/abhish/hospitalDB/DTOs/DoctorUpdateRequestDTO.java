package com.abhish.hospitalDB.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorUpdateRequestDTO {


    private String name;
    @Email
    private String email;
    private String specialization;
}

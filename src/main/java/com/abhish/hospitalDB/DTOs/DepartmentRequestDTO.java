package com.abhish.hospitalDB.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DepartmentRequestDTO {

    @NotNull
    private String name;
    private Long headDoctorId;
}

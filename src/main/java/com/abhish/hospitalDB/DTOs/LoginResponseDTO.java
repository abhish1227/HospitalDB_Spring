package com.abhish.hospitalDB.DTOs;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private String jwt;
    private Long id;

}

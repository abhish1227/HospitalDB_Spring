package com.abhish.hospitalDB.DTOs;


import lombok.Data;

@Data
public class LoginRequestDTO {

    private String username;
    private String password;
}

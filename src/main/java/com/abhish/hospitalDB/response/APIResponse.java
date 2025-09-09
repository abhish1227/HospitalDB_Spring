package com.abhish.hospitalDB.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class APIResponse {
    public String message;
    public Object data;
}

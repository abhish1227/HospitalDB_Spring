package com.abhish.hospitalDB.errorHandler;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class APIErrorResponse {

    private LocalDateTime timeStamp;
    private String error;
    private HttpStatus statusCode;

    public APIErrorResponse(){
        this.timeStamp = LocalDateTime.now();
    }

    public APIErrorResponse(String error, HttpStatus statusCode){
        this();
        this.error = error;
        this.statusCode = statusCode;
    }


}

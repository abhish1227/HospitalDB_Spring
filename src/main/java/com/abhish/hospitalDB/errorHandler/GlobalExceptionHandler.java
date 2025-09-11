package com.abhish.hospitalDB.errorHandler;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler { /*catches errors that occurs in spring's MVC architecture but not before it.
    i.e. errors/exceptions that occurs during the security filter chain stage are not caught by this
    In order to catch filter chain exception in the MVC we use HandlerExceptionResolver which catches the exception
    and sends it to global exception handler.*/

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<APIErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e){
        APIErrorResponse apiErrorResponse =
                new APIErrorResponse("Username not found: "+e.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatusCode());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<APIErrorResponse> handleAuthenticationException(AuthenticationException e){
        APIErrorResponse apiErrorResponse =
                new APIErrorResponse("Authentication failed: "+e.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatusCode());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<APIErrorResponse> handleJwtException(JwtException e){
        APIErrorResponse apiErrorResponse =
                new APIErrorResponse("Invalid JWT token: "+e.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatusCode());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIErrorResponse> handleAccessDeniedException(AccessDeniedException e){
        APIErrorResponse apiErrorResponse =
                new APIErrorResponse("Access denied: Insufficient permission. "+e.getMessage(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIErrorResponse> handleGeneralException(Exception e){
        APIErrorResponse apiErrorResponse =
                new APIErrorResponse("An unexpected error occurred: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatusCode());
    }
}

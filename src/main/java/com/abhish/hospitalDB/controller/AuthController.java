package com.abhish.hospitalDB.controller;


import com.abhish.hospitalDB.DTOs.LoginRequestDTO;
import com.abhish.hospitalDB.response.APIResponse;
import com.abhish.hospitalDB.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestBody LoginRequestDTO loginRequest){
        return ResponseEntity.ok()
                .body(new APIResponse("Login successful.", authService.login(loginRequest)));

    }

    @PostMapping("/signup")
    public ResponseEntity<APIResponse> signup(@RequestBody LoginRequestDTO signupRequest){
        try {
            return ResponseEntity.ok()
                    .body(new APIResponse("User created successfully.", authService.signup(signupRequest)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        } catch(Exception e){
           return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }
}

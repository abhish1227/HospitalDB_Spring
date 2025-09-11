package com.abhish.hospitalDB.security;


import com.abhish.hospitalDB.DTOs.LoginRequestDTO;
import com.abhish.hospitalDB.DTOs.LoginResponseDTO;
import com.abhish.hospitalDB.DTOs.SignupResponseDTO;
import com.abhish.hospitalDB.entity.User;
import com.abhish.hospitalDB.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDTO(token, user.getId());

    }

    public SignupResponseDTO signup(LoginRequestDTO signupRequest) {
        User user = userRepository.findByUsername(signupRequest.getUsername())
                .orElse(null);

        if(user!=null) throw new IllegalArgumentException("User already exists with the given username");


        user = userRepository.save(User.builder()
                .username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .build()
        );

        return new SignupResponseDTO(user.getId(), user.getUsername());
    }
}

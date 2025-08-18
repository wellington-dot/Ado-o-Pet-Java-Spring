package com.pet.AdoteUmPet.controllers;

import com.pet.AdoteUmPet.dto.JwtResponse;
import com.pet.AdoteUmPet.dto.LoginRequest;
import com.pet.AdoteUmPet.security.UsuarioDetailsServiceImpl;
import com.pet.AdoteUmPet.security.jwt.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;
    private final UsuarioDetailsServiceImpl usuarioDetailsService;

    public AuthController(AuthenticationManager authManager, JwtUtils jwtUtils, UsuarioDetailsServiceImpl usuarioDetailsService){
        this.authManager = authManager;
        this.jwtUtils = jwtUtils;
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req){
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        String token = jwtUtils.gerarToken(req.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}

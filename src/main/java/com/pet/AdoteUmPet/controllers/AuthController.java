package com.pet.AdoteUmPet.controllers;

import com.pet.AdoteUmPet.dto.JwtResponse;
import com.pet.AdoteUmPet.dto.LoginRequest;
import com.pet.AdoteUmPet.security.UsuarioDetailsServiceImpl;
import com.pet.AdoteUmPet.security.jwt.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
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
        System.out.println("MÉTODO LOGIN CHAMADO!");
        System.out.println("Username: " + req.getUsername());
        System.out.println("Password: " + req.getPassword());
        try{
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
            System.out.println("AUTENTICAÇÃO OK!");
            String token = jwtUtils.gerarToken(req.getUsername());
            System.out.println("TOKEN GERADO: " + token);

            return ResponseEntity.ok(new JwtResponse(token));
        }catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(403).body("Erro: " + e.getMessage());
        }

    }
}

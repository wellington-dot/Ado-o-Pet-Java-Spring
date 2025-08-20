package com.pet.AdoteUmPet.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    //Variavel da chave secreta para assinatura
    @Value("${jwt.secret}")
    private String jwtSecret;

    //Variavel que define o tempo de expiração do token
    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    //Metodo para gerar a chave de assinatura (usa a string de chave secreta jwtSecret, e transforma em chave segura)
    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // Metodo para gerar o token a partir do email do usuario
    public String gerarToken(String username) {
        return Jwts.builder()
                .setSubject(username) // o "assunto" do token – geralmente o email ou nome do usuário
                .setIssuedAt(new Date()) // data de criação
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // data de expiração
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // assina com chave e algoritmo
                .compact(); // gera o token final
    }

    // Metodo para extrair o email do token
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // mesma chave que usou para assinar
                .build()
                .parseClaimsJws(token) // faz o parsing do token
                .getBody()
                .getSubject(); // pega o campo "subject" (que é o username)
    }

    // Metodo para validar se o token é válido
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token); // se não lançar erro, está válido
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Captura vários tipos de erro possíveis: token inválido, expirado, etc.
            System.out.println("Token inválido: " + e.getMessage());
        }
        return false;
    }
}

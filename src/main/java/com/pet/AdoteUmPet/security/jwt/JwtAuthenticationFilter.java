package com.pet.AdoteUmPet.security.jwt;

import com.pet.AdoteUmPet.security.UsuarioDetailsImpl;
import com.pet.AdoteUmPet.security.UsuarioDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Executado uma vez por requisição
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils; //Classe para validar o token
    private final UsuarioDetailsServiceImpl usuarioDetailsService; //Classe para carregar o usuário

    //Injetar as dependencias
    public JwtAuthenticationFilter(JwtUtils jwtUtils, UsuarioDetailsServiceImpl usuarioDetailsService){
        this.jwtUtils = jwtUtils;
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException{

        //Pega o Header "Authorization" da requisição
        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        //Verifica se o header não é nulo e se começa com "Bearer"
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            //Extrai somente o token
            jwtToken = authHeader.substring(7);
            try{
                //Extrai o usuário do token
                username = jwtUtils.getUsernameFromToken(jwtToken);
            } catch (Exception e){
                System.out.println("Token JWT inválido ou expirado");
            }
        }

        //Se o username não for nulo(existe) e não estiver autenticado
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            //Busca os dados do usuário pelo username
            UsuarioDetailsImpl usuarioDetails = this.usuarioDetailsService.loadUserByUsername(username);

            if (jwtUtils.validarToken(jwtToken)){
                //Token válido, autentica o usuário
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(usuarioDetails, null, usuarioDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        //Continuar filtro da cadeia
        filterChain.doFilter(request,response);
    }

}

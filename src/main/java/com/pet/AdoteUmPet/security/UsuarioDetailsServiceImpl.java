package com.pet.AdoteUmPet.security;

import com.pet.AdoteUmPet.model.entities.Usuario;
import com.pet.AdoteUmPet.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    //Instancia o repository do usuário
    private final UsuarioRepository usuarioRepository;

    private UsuarioDetailsServiceImpl(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    //Chamar nosso repository para buscar o usuario no banco pelo email
    @Override
    public UsuarioDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
        return new UsuarioDetailsImpl(usuario);
    }
}

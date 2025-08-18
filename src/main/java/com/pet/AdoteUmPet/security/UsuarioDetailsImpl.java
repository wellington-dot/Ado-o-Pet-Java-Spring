package com.pet.AdoteUmPet.security;


import com.pet.AdoteUmPet.model.entities.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class UsuarioDetailsImpl implements UserDetails {

    //Instancia da entidade usuario
    private final Usuario usuario;

    //Recebe um usuario e armazena internamente
    public UsuarioDetailsImpl(Usuario usuario){
        this.usuario = usuario;
    }

    //Definir quais permissões/roles o usuário possui
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name()));
    }

    //Retorna a senha
    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    //Retorna o email que está sendo usado para login
    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    //Métodos de status
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    //Retorna o objeto do usuário caso necessário
    public Usuario getUsuario() {
        return usuario;
    }
}

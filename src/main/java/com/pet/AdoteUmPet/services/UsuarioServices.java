package com.pet.AdoteUmPet.services;

import com.pet.AdoteUmPet.model.entities.Usuario;
import com.pet.AdoteUmPet.repository.AdocaoRepository;
import com.pet.AdoteUmPet.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServices {

    private final UsuarioRepository usuarioRepository;
    private final AdocaoRepository adocaoRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServices(UsuarioRepository usuarioRepository, AdocaoRepository adocaoRepository, PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.adocaoRepository = adocaoRepository;
        this.passwordEncoder = passwordEncoder;
    }


    //Retornar lista de usuario
    public List<Usuario> retornarUsuarios(){
        return usuarioRepository.findAll();
    }

    //Salvar novo usuario
    public void adicionarUsuario(Usuario usuario){
        if (usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new RuntimeException("Email já cadastrado");
        }
        //Criptografa a senha
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        System.out.println(usuario);
        //Salva no banco
        usuarioRepository.save(usuario);
    }

    //Deletar um usuario pelo id
    public void deletarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }

    //Atualizar um usuario
    public void atualizarUsuario(Long id, Usuario attUsuario){
        Usuario usuarioEx = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adotante com ID " + id + " não encontrado"));

        usuarioEx.setNome(attUsuario.getNome());
        usuarioEx.setEmail(attUsuario.getEmail());
        usuarioEx.setTelefone(attUsuario.getTelefone());
        usuarioEx.setCpf(attUsuario.getCpf());

        usuarioRepository.save(usuarioEx);
    }

}

package com.pet.AdoteUmPet.services;

import com.pet.AdoteUmPet.model.entities.Usuario;
import com.pet.AdoteUmPet.repository.AdocaoRepository;
import com.pet.AdoteUmPet.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServices {

    private final UsuarioRepository usuarioRepository;
    private final AdocaoRepository adocaoRepository;

    public UsuarioServices(UsuarioRepository usuarioRepository, AdocaoRepository adocaoRepository){
        this.usuarioRepository = usuarioRepository;
        this.adocaoRepository = adocaoRepository;
    }


    //Retornar lista de usuario
    public List<Usuario> retornarUsuarios(){
        return usuarioRepository.findAll();
    }

    //Salvar novo usuario
    public void adicionarUsuario(Usuario body){
        usuarioRepository.save(body);
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

package com.pet.AdoteUmPet.controllers;

import com.pet.AdoteUmPet.model.entities.Usuario;
import com.pet.AdoteUmPet.services.UsuarioServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioServices usuarioServices;

    public UsuarioController(UsuarioServices usuarioServices){
        this.usuarioServices = usuarioServices;
    }

    //Retornar a lista de usuarios do bd
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> retornarUsuarios(){
        try{
            List<Usuario> listaUsuario = usuarioServices.retornarUsuarios();
            return ResponseEntity.status(HttpStatus.OK).body(listaUsuario);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    //Salvar novo usuario
    @PostMapping
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<?> adicionarUsuario(@RequestBody Usuario usuario){
        try{
            usuarioServices.adicionarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro " + e, "Erro ao adicionar adotante.."));
        }
    }

    //Deletar um usuario pelo id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id){
        try{
            usuarioServices.deletarUsuario(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro ao deletar adotante.."));
        }
    }

    //Atualizar cadas de um usuario
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario attUsuario){
        try{
            usuarioServices.atualizarUsuario(id, attUsuario);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", "Não foi possivel atualizar esse usuario, verifique o id.."));
        }
    }
}

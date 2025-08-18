package com.pet.AdoteUmPet.repository;

import com.pet.AdoteUmPet.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //Buscar o usuario no banco através do email
    Optional<Usuario> findByEmail(String email);

}

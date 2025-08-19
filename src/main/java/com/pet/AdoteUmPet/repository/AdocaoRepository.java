package com.pet.AdoteUmPet.repository;

import com.pet.AdoteUmPet.model.entities.Adocao;
import com.pet.AdoteUmPet.model.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdocaoRepository extends JpaRepository<Adocao, Long> {

    Optional<Adocao> findByPetIdAndUsuarioId(Long petId, Long usuarioId);

    @Query(value = "SELECT p.* FROM pet p JOIN adocao a ON p.id = a.pet_id WHERE a.usuario_id = :idUsuario", nativeQuery = true)
    List<Pet> findPetsByUsuarioId(@RequestParam Long idUsuario);

    @Query(value = "SELECT p.* FROM pet p WHERE p.adotado = 0", nativeQuery = true)
    List<Pet> findPetsDisponiveis();
}

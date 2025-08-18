package com.pet.AdoteUmPet.controllers;

import com.pet.AdoteUmPet.model.entities.Pet;
import com.pet.AdoteUmPet.services.PetServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetServices petServices;

    public PetController(PetServices petServices){
        this.petServices = petServices;
    }

    //Buscar lista de pets
    @GetMapping
    public ResponseEntity<List<Pet>> retornarPets(){
        try{
            List<Pet> pets = petServices.listarTodos();
            return ResponseEntity.status(HttpStatus.OK).body(pets);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Adicionar novo pet
    @PostMapping("/add")
    public ResponseEntity<?> adicionarPet(@RequestBody Pet pet){
        try {
            //Forçando sempre como false, pois o pet acabou der ser criado
            pet.setAdotado(false);
            petServices.adicionar(pet);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro para adicionar novo pet."));
        }
    }

    //Excluir um pet
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPet(@PathVariable Long id){
        try{
            petServices.deletePet(id);
            return  ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro ao deletar Pet."));
        }
    }

    //Atualizar o cadas de um pet
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarInfoPet(@PathVariable Long id, @RequestBody Pet petAtualizado){
        try{
            petServices.atualizarPet(id, petAtualizado);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", "Erro ao atualizar cadastro de pet."));
        }
    }
}

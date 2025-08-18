package com.pet.AdoteUmPet.controllers;

import com.pet.AdoteUmPet.model.entities.Adocao;
import com.pet.AdoteUmPet.model.entities.Pet;
import com.pet.AdoteUmPet.services.AdocaoServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/adocao")
public class AdocaoController {

    private final AdocaoServices adocaoServices;

    public AdocaoController(AdocaoServices adocaoServices){
        this.adocaoServices = adocaoServices;
    }

    //Traz a lista de todas as adoções do sistema
    @GetMapping
    public ResponseEntity<List<Adocao>> listaAdocoes(){
        try{
            List<Adocao> adocoes = adocaoServices.listaAdocao();
            return ResponseEntity.status(HttpStatus.OK).body(adocoes);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Traz os pets que não foram adotados ainda
    @GetMapping(value = "/disponiveis")
    public ResponseEntity<List<Pet>> petsDisponiveis(){
        try {
            List<Pet> petsDisponiveis = adocaoServices.listaPetsDisponiveis();
            return ResponseEntity.status(HttpStatus.OK).body(petsDisponiveis);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Trazer todos os pets adotados por um adotante
    @PostMapping(value = "/{id}")
    public ResponseEntity<List<Pet>> retornarPets(@PathVariable Long id){
        try{
            List<Pet> pets = adocaoServices.petsAdotados(id);
            return ResponseEntity.status(HttpStatus.OK).body(pets);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Criar uma adoção no sistema
    @PostMapping
    public ResponseEntity<?> adotarPet(
            @RequestParam(name="idPet", required = true)Long idPet,
            @RequestParam(name="idAdotante", required = true)Long idAdotante){
        try{
            adocaoServices.adotarPet(idPet, idAdotante);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Devolver uma adoçao
    @PostMapping(value ="/devolucao")
    public ResponseEntity<?> devolverAdocao(
            @RequestParam(name="idPet", required = true)Long idPet,
            @RequestParam(name="idAdotante", required = true)Long idAdotante){
        try{
            adocaoServices.devolucaoPet(idPet, idAdotante);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", "Não foi possível concluir a devolução, verifique as informações."));
        }
    }
}


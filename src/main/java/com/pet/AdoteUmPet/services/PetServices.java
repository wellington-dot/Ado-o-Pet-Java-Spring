package com.pet.AdoteUmPet.services;

import com.pet.AdoteUmPet.model.entities.Pet;
import com.pet.AdoteUmPet.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServices {

    private final PetRepository petRepository;

    public PetServices(PetRepository petRepository){
        this.petRepository = petRepository;
    }

    public List<Pet> listarTodos(){
        return petRepository.findAll();
    }

    public void adicionar(Pet pet){
        //setando adotado como false
        pet.setAdotado(false);
        petRepository.save(pet);
    }

    public void deletePet(Long id){
        petRepository.deleteById(id);
    }

    public void atualizarPet(Long id, Pet petAtualizado){
        Pet petExistente = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet com ID " + id + " não encontrado"));

        petExistente.setNome(petAtualizado.getNome());
        petExistente.setTipo(petAtualizado.getTipo());
        petExistente.setRaca(petAtualizado.getRaca());
        petExistente.setIdade(petAtualizado.getIdade());
        petExistente.setPorte(petAtualizado.getPorte());
        petExistente.setAdotado(petAtualizado.isAdotado());

        petRepository.save(petExistente);
    }
}

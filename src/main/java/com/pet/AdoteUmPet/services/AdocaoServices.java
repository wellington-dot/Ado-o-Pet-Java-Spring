package com.pet.AdoteUmPet.services;

import com.pet.AdoteUmPet.model.entities.Adocao;
import com.pet.AdoteUmPet.model.entities.Usuario;
import com.pet.AdoteUmPet.model.entities.Pet;
import com.pet.AdoteUmPet.repository.AdocaoRepository;
import com.pet.AdoteUmPet.repository.UsuarioRepository;
import com.pet.AdoteUmPet.repository.PetRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AdocaoServices {

    private final AdocaoRepository adocaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PetRepository petRepository;

    public AdocaoServices(AdocaoRepository adocaoRepository, UsuarioRepository usuarioRepository, PetRepository petRepository) {
        this.adocaoRepository = adocaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.petRepository = petRepository;
    }

    //Retorna lista de adoções do sistema
    public List<Adocao> listaAdocao() {
        return adocaoRepository.findAll();
    }

    //Metodo para adotar um pet
    public void adotarPet(Long idPet, Long idAdotante) {
        //Verifica se o pet e o adotante existe no banco de dados
        Optional<Pet> petExiste = petRepository.findById(idPet);
        Optional<Usuario> adotanteExiste = usuarioRepository.findById(idAdotante);

        //Verifica o retorno, se for null lança uma excesão, caso não seja, instancia uma nova adoção e salva no banco de dados
        if (petExiste.isEmpty() || adotanteExiste.isEmpty()) {
            throw new RuntimeException("Pet ou Adotante não encontrado.");

        } else if (petExiste.get().isAdotado()) {
            throw new RuntimeException("Não é possível adotar esse pet, pois já foi adotado!");
        } else {
            Adocao adocao = new Adocao();
            //Adiciona o pet e o adotante na instancia, muda o status do pet para adotado
            petExiste.get().setAdotado(true);
            adocao.setPet(petExiste.get());
            adocao.setUsuario(adotanteExiste.get());
            adocao.setDataAdocao(LocalDate.now());
            //Salva no BD
            adocaoRepository.save(adocao);
        }
    }

    //Metodo para excluir uma adoção do sistema
    public void devolucaoPet(Long idPet, Long idAdotante) {
        Optional<Pet> petExiste = petRepository.findById(idPet);
        Optional<Usuario> adotanteExiste = usuarioRepository.findById(idAdotante);

        if (petExiste.isEmpty() || adotanteExiste.isEmpty()) {
            throw new RuntimeException("Pet ou Adotante não encontrado.");
        } else if (!petExiste.get().isAdotado()) {
            throw new RuntimeException("Não foi possivel devolver, pois esse pet ainda não foi adotado.");
        } else {
            //Busca adoção expecífica
            Optional<Adocao> adocao = adocaoRepository.findByPetIdAndUsuarioId(idPet, idAdotante);

            //Excluindo adoção do sistema
            if(adocao.isPresent()){
                petExiste.get().setAdotado(false);
                petRepository.save(petExiste.get()); //Salva a mudança do pet - libera a adocao agora
                adocaoRepository.deleteById(adocao.get().getId()); // Deleta pela ID da adoção
            }
        }
    }

    //Metodo para trazer todos os pets adotados por um adotante
    public List<Pet> petsAdotados(Long idAdotante){
        List<Pet> pets = adocaoRepository.findPetsByUsuarioId(idAdotante);
        return pets;
    }

    //Metodo para trazer os pets que não foram adotados ainda
    public List<Pet> listaPetsDisponiveis(){
        List<Pet> petsDisponiveis = adocaoRepository.findPetsDisponiveis();
        return petsDisponiveis;
    }
}
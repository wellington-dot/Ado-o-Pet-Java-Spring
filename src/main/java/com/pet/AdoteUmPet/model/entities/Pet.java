package com.pet.AdoteUmPet.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//Classe dos atributos dos pets
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Pet {

    public Pet (){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String tipo;
    private String raca;
    private Integer idade;
    private String porte;
    private boolean adotado;
}

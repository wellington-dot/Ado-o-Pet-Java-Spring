package com.pet.AdoteUmPet.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//Classe com os atributos do adotante
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Usuario {

    public Usuario() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String password;
    private String telefone;
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

}

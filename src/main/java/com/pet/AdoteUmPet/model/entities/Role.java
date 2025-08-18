package com.pet.AdoteUmPet.model.entities;

public enum Role {
    ADMIN("Admin"),
    ADOTANTE("Adotante");

    private String descricao;

    Role(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

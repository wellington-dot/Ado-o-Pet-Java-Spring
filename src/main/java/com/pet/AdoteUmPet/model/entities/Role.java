package com.pet.AdoteUmPet.model.entities;

public enum Role {
    ADMIN("Admin"),
    USUARIO("Usuario");

    private String descricao;

    Role(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

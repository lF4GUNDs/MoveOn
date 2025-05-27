package com.example.moveon.models;

public class Perfil {
    private String nome;
    private int imagemResId; // Você pode trocar para URI futuramente

    public Perfil(String nome, int imagemResId) {
        this.nome = nome;
        this.imagemResId = imagemResId;
    }

    public String getNome() {
        return nome;
    }

    public int getImagemResId() {
        return imagemResId;
    }
}

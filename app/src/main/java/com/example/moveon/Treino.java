package com.example.moveon;

public class Treino {
    private String nome;
    private int imagemResId;

    public Treino(String nome, int imagemResId) {
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

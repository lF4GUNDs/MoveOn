package com.example.moveon.models;

import java.io.Serializable;

public class Perfil implements Serializable {
    private int id;  // Novo campo ID
    private String nome;
    private int imagemResId;

    // Construtor com ID (útil ao carregar de armazenamento)
    public Perfil(int id, String nome, int imagemResId) {
        this.id = id;
        this.nome = nome;
        this.imagemResId = imagemResId;
    }

    // Construtor sem ID (útil ao criar novo perfil)
    public Perfil(String nome, int imagemResId) {
        this.nome = nome;
        this.imagemResId = imagemResId;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public int getImagemResId() {
        return imagemResId;
    }
}

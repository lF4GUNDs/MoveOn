package com.example.moveon.models;

import java.io.Serializable;

public class Exercicio implements Serializable {
    private String nome;
    private int series;
    private int reps;
    private int peso;

    // Construtor completo
    public Exercicio(String nome, int series, int reps, int peso) {
        this.nome = nome;
        this.series = series;
        this.reps = reps;
        this.peso = peso;
    }

    // Novo construtor com valores padrão para reps e peso
    public Exercicio(String nome, int series) {
        this.nome = nome;
        this.series = series;
        this.reps = 12; // padrão
        this.peso = 30; // padrão
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public int getSeries() {
        return series;
    }

    public int getReps() {
        return reps;
    }

    public int getPeso() {
        return peso;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
}

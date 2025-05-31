package com.example.moveon.models;

import java.io.Serializable;

public class Exercicio implements Serializable {

    private int id;  // ID para uso no banco de dados
    private String nome;
    private int series;
    private int reps;
    private float peso;  // ALTERADO para float
    private int seriesConcluidas = 0;

    // Construtor principal
    public Exercicio(String nome, int series, float peso, int reps) {
        this.nome = nome;
        this.series = series;
        this.peso = peso;
        this.reps = reps;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getSeries() { return series; }
    public int getReps() { return reps; }
    public float getPeso() { return peso; }  // float
    public int getSeriesConcluidas() { return seriesConcluidas; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setSeries(int series) { this.series = series; }
    public void setReps(int reps) { this.reps = reps; }
    public void setPeso(float peso) { this.peso = peso; }  // float
    public void setSeriesConcluidas(int seriesConcluidas) { this.seriesConcluidas = seriesConcluidas; }

    // Métodos utilitários
    public void incrementarSerie() {
        if (seriesConcluidas < series) {
            seriesConcluidas++;
        }
    }

    public void resetSeriesConcluidas() {
        seriesConcluidas = 0;
    }
}

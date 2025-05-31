package com.example.moveon.models;

import java.io.Serializable;

public class Exercicio implements Serializable {

    private int id;
    private int treinoId;
    private int perfilId;
    private String nome;
    private int series;
    private int reps;
    private float peso;
    private int seriesConcluidas = 0;

    public Exercicio(String nome, int series, float peso, int reps) {
        this.nome = nome;
        this.series = series;
        this.peso = peso;
        this.reps = reps;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTreinoId() { return treinoId; }
    public void setTreinoId(int treinoId) { this.treinoId = treinoId; }

    public int getPerfilId() { return perfilId; }
    public void setPerfilId(int perfilId) { this.perfilId = perfilId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getSeries() { return series; }
    public void setSeries(int series) { this.series = series; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    public int getPeso() { return (int) peso; }
    public void setPeso(float peso) { this.peso = peso; }

    public int getSeriesConcluidas() { return seriesConcluidas; }
    public void setSeriesConcluidas(int seriesConcluidas) { this.seriesConcluidas = seriesConcluidas; }

    public void incrementarSerie() {
        if (seriesConcluidas < series) {
            seriesConcluidas++;
        }
    }

    public void resetSeriesConcluidas() {
        seriesConcluidas = 0;
    }
}

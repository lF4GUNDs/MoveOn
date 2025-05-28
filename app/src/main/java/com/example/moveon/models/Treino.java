package com.example.moveon.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Treino implements Serializable {
    private String nome;
    private int imagemResId;
    private int totalSeries;
    private int reps;
    private ArrayList<Exercicio> listaExercicios; // Associar exercícios

    public Treino(String nome, int imagemResId) {
        this(nome, imagemResId, 4, 12); // Valores padrão
    }

    public Treino(String nome, int imagemResId, int totalSeries, int reps) {
        this.nome = nome;
        this.imagemResId = imagemResId;
        this.totalSeries = totalSeries;
        this.reps = reps;
        this.listaExercicios = new ArrayList<>(); // Inicializar lista
    }

    public String getNome() {
        return nome;
    }

    public int getImagemResId() {
        return imagemResId;
    }

    public int getTotalSeries() {
        return totalSeries;
    }

    public int getReps() {
        return reps;
    }

    public ArrayList<Exercicio> getListaExercicios() {
        return listaExercicios;
    }

    public void setListaExercicios(ArrayList<Exercicio> listaExercicios) {
        this.listaExercicios = listaExercicios;
    }
}

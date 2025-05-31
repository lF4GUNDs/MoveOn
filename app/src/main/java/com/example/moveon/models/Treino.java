package com.example.moveon.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Treino implements Serializable {

    private int id; // Identificador único do treino
    private String nome;
    private int imagemResId;
    private int totalSeries;
    private int reps;
    private ArrayList<Exercicio> listaExercicios;

    // Construtor padrão (sem ID, útil para novos treinos)
    public Treino(String nome, int imagemResId) {
        this(nome, imagemResId, 4, 12);
    }

    public Treino(String nome, int imagemResId, int totalSeries, int reps) {
        this.nome = nome;
        this.imagemResId = imagemResId;
        this.totalSeries = totalSeries;
        this.reps = reps;
        this.listaExercicios = new ArrayList<>();
    }

    // Construtor com ID (usado para casos com controle direto de ID fixo)
    public Treino(int id, String nome, int imagemResId, int totalSeries, int reps) {
        this.id = id;
        this.nome = nome;
        this.imagemResId = imagemResId;
        this.totalSeries = totalSeries;
        this.reps = reps;
        this.listaExercicios = new ArrayList<>();
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getImagemResId() {
        return imagemResId;
    }

    public void setImagemResId(int imagemResId) {
        this.imagemResId = imagemResId;
    }

    public int getTotalSeries() {
        return totalSeries;
    }

    public void setTotalSeries(int totalSeries) {
        this.totalSeries = totalSeries;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public ArrayList<Exercicio> getListaExercicios() {
        return listaExercicios;
    }

    public void setListaExercicios(ArrayList<Exercicio> listaExercicios) {
        this.listaExercicios = listaExercicios;
    }
}

package com.example.moveon.models;

public class HistoricoTreino {
    private String data;
    private String nomeGrupo;
    private int duracaoMinutos;

    public HistoricoTreino(String data, String nomeGrupo, int duracaoMinutos) {
        this.data = data;
        this.nomeGrupo = nomeGrupo;
        this.duracaoMinutos = duracaoMinutos;
    }

    public String getData() {
        return data;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }
}

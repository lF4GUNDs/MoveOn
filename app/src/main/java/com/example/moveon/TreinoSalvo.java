package com.example.moveon;

public class TreinoSalvo {
    private String nome;
    private int seriesFeitas;
    private int seriesTotais;

    public TreinoSalvo(String nome, int seriesFeitas, int seriesTotais) {
        this.nome = nome;
        this.seriesFeitas = seriesFeitas;
        this.seriesTotais = seriesTotais;
    }

    public String getNome() { return nome; }
    public int getSeriesFeitas() { return seriesFeitas; }
    public int getSeriesTotais() { return seriesTotais; }
}

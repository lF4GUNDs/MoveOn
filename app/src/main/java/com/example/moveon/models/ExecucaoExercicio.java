package com.example.moveon.models;

public class ExecucaoExercicio {
    private int id;
    private int perfilId;
    private String data;
    private String nomeExercicio;
    private int serie;
    private int peso;
    private int reps;
    private int descanso;

    public ExecucaoExercicio(int id, int perfilId, String data, String nomeExercicio, int serie, int peso, int reps, int descanso) {
        this.id = id;
        this.perfilId = perfilId;
        this.data = data;
        this.nomeExercicio = nomeExercicio;
        this.serie = serie;
        this.peso = peso;
        this.reps = reps;
        this.descanso = descanso;
    }

    // Getters e setters (opcional)
}

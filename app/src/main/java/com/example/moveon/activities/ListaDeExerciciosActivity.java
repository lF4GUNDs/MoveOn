package com.example.moveon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;
import com.example.moveon.adapters.ExercicioListaAdapter;
import com.example.moveon.models.Exercicio;
import com.example.moveon.models.Treino;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaDeExerciciosActivity extends AppCompatActivity {

    private RecyclerView recyclerExercicios;
    private FloatingActionButton btnAdicionarExercicio;
    private MaterialButton btnIniciar;
    private ExercicioListaAdapter adapter;
    private ArrayList<Exercicio> listaExercicios;
    private Treino treinoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_exercicios);

        // Referências
        recyclerExercicios = findViewById(R.id.recyclerExercicios);
        btnAdicionarExercicio = findViewById(R.id.btnAdicionarExercicio);
        btnIniciar = findViewById(R.id.btnIniciarExecucao);

        // Recuperar o treino da intent
        treinoSelecionado = (Treino) getIntent().getSerializableExtra("treino");

        if (treinoSelecionado != null) {
            listaExercicios = treinoSelecionado.getListaExercicios();
        } else {
            listaExercicios = new ArrayList<>();
        }

        adapter = new ExercicioListaAdapter(listaExercicios);
        recyclerExercicios.setLayoutManager(new LinearLayoutManager(this));
        recyclerExercicios.setAdapter(adapter);

        btnIniciar.setOnClickListener(v -> {
            if (!listaExercicios.isEmpty()) {
                Intent intent = new Intent(this, ExecutarExercicioActivity.class);
                intent.putExtra("exercicio", listaExercicios.get(0)); // exemplo: o primeiro
                startActivity(intent);
            }
        });

        btnAdicionarExercicio.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdicionarExercicioActivity.class);
            startActivity(intent); // ou startActivityForResult se quiser retorno
        });
    }
}

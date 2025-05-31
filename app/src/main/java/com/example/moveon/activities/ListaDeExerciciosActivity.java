package com.example.moveon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;
import com.example.moveon.adapters.ExercicioListaAdapter;
import com.example.moveon.database.ExerciciosDBHelper;
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
    private ExerciciosDBHelper dbHelper;

    private ActivityResultLauncher<Intent> launcherAdicionarExercicio;
    private ActivityResultLauncher<Intent> launcherEditarExercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_exercicios);

        recyclerExercicios = findViewById(R.id.recyclerExercicios);
        btnAdicionarExercicio = findViewById(R.id.btnAdicionarExercicio);
        btnIniciar = findViewById(R.id.btnIniciarExecucao);

        treinoSelecionado = (Treino) getIntent().getSerializableExtra("treino");
        dbHelper = new ExerciciosDBHelper(this);

        if (treinoSelecionado != null) {
            listaExercicios = dbHelper.listarExerciciosPorTreino(treinoSelecionado.getId());
        } else {
            listaExercicios = new ArrayList<>();
        }

        adapter = new ExercicioListaAdapter(listaExercicios, this::editarExercicio, this::excluirExercicio);
        recyclerExercicios.setLayoutManager(new LinearLayoutManager(this));
        recyclerExercicios.setAdapter(adapter);

        btnIniciar.setOnClickListener(v -> {
            if (!listaExercicios.isEmpty()) {
                Intent intent = new Intent(this, ExecutarExercicioActivity.class);
                intent.putExtra("exercicio", listaExercicios.get(0));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Nenhum exercício adicionado!", Toast.LENGTH_SHORT).show();
            }
        });

        launcherAdicionarExercicio = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        listaExercicios.clear();
                        listaExercicios.addAll(dbHelper.listarExerciciosPorTreino(treinoSelecionado.getId()));
                        adapter.notifyDataSetChanged();
                    }
                }
        );

        launcherEditarExercicio = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        listaExercicios.clear();
                        listaExercicios.addAll(dbHelper.listarExerciciosPorTreino(treinoSelecionado.getId()));
                        adapter.notifyDataSetChanged();
                    }
                }
        );

        btnAdicionarExercicio.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdicionarExercicioActivity.class);
            intent.putExtra("treinoId", treinoSelecionado.getId());
            launcherAdicionarExercicio.launch(intent);
        });
    }

    private void editarExercicio(Exercicio exercicio, int posicao) {
        Intent intent = new Intent(this, EditarExercicioActivity.class);
        intent.putExtra("exercicio", exercicio);
        intent.putExtra("posicao", posicao);
        launcherEditarExercicio.launch(intent);
    }

    private void excluirExercicio(int posicao) {
        if (posicao >= 0 && posicao < listaExercicios.size()) {
            Exercicio exercicio = listaExercicios.get(posicao);
            dbHelper.excluirExercicio(exercicio.getId());
            listaExercicios.remove(posicao);
            adapter.notifyItemRemoved(posicao);
        }
    }
}

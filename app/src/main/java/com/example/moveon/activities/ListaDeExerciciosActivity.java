package com.example.moveon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;
import com.example.moveon.adapters.ExercicioListaAdapter;
import com.example.moveon.models.Exercicio;
import com.example.moveon.models.Treino;

import java.util.ArrayList;

public class ListaDeExerciciosActivity extends AppCompatActivity {

    private RecyclerView recyclerExercicios;
    private Button btnIniciar, btnAdicionarExercicio;
    private ExercicioListaAdapter adapter;
    private ArrayList<Exercicio> listaExercicios;
    private Treino treinoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_exercicios);

        recyclerExercicios = findViewById(R.id.recyclerExercicios);
        btnIniciar = findViewById(R.id.btnIniciarExecucao);
        btnAdicionarExercicio = findViewById(R.id.btnAdicionarExercicio); // Novo botão no layout

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
                intent.putExtra("exercicio", listaExercicios.get(0));
                startActivity(intent);
            }
        });

        btnAdicionarExercicio.setOnClickListener(v -> mostrarDialogAdicionarExercicio());
    }

    private void mostrarDialogAdicionarExercicio() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Novo Exercício");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText inputNome = new EditText(this);
        inputNome.setHint("Nome do exercício");
        layout.addView(inputNome);

        final EditText inputSeries = new EditText(this);
        inputSeries.setHint("Nº de séries");
        inputSeries.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(inputSeries);

        builder.setView(layout);

        builder.setPositiveButton("Adicionar", (dialog, which) -> {
            String nome = inputNome.getText().toString().trim();
            int series = Integer.parseInt(inputSeries.getText().toString().trim());

            if (!nome.isEmpty() && series > 0) {
                Exercicio novo = new Exercicio(nome, series);
                treinoSelecionado.getListaExercicios().add(novo);
                adapter.notifyItemInserted(listaExercicios.size() - 1);
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
}

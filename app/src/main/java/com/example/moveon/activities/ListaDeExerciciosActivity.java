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

    private int perfilId;

    private ActivityResultLauncher<Intent> launcherAdicionarExercicio;
    private ActivityResultLauncher<Intent> launcherEditarExercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_exercicios);

        // Inicializações
        recyclerExercicios = findViewById(R.id.recyclerExercicios);
        btnAdicionarExercicio = findViewById(R.id.btnAdicionarExercicio);
        btnIniciar = findViewById(R.id.btnIniciarExecucao);
        dbHelper = new ExerciciosDBHelper(this);

        // Recupera dados da intent
        treinoSelecionado = (Treino) getIntent().getSerializableExtra("treino");
        perfilId = getIntent().getIntExtra("perfilId", -1);

        if (treinoSelecionado == null || perfilId == -1) {
            Toast.makeText(this, "Dados inválidos!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Carrega exercícios do treino e perfil
        listaExercicios = dbHelper.listarExerciciosPorTreino(treinoSelecionado.getId(), perfilId);
        if (listaExercicios.isEmpty()) {
            dbHelper.preencherExerciciosPadrao(treinoSelecionado.getId(), treinoSelecionado.getNome(), perfilId);
            listaExercicios = dbHelper.listarExerciciosPorTreino(treinoSelecionado.getId(), perfilId);
            Toast.makeText(this, "Exercícios padrão adicionados!", Toast.LENGTH_SHORT).show();
        }

        // Configura o adapter e RecyclerView
        adapter = new ExercicioListaAdapter(listaExercicios, this::editarExercicio, this::excluirExercicio);
        recyclerExercicios.setLayoutManager(new LinearLayoutManager(this));
        recyclerExercicios.setAdapter(adapter);

        // Iniciar treino com lista completa
        btnIniciar.setOnClickListener(v -> {
            if (!listaExercicios.isEmpty()) {
                Intent intent = new Intent(this, ExecutarExercicioActivity.class);
                intent.putExtra("listaExercicios", listaExercicios); // ✅ Envia lista completa
                intent.putExtra("perfilId", perfilId);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Nenhum exercício adicionado!", Toast.LENGTH_SHORT).show();
            }
        });

        // Lançador para adicionar exercício
        launcherAdicionarExercicio = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        recarregarLista();
                    }
                }
        );

        // Lançador para editar exercício
        launcherEditarExercicio = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        recarregarLista();
                    }
                }
        );

        // Ação de adicionar exercício
        btnAdicionarExercicio.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdicionarExercicioActivity.class);
            intent.putExtra("treinoId", treinoSelecionado.getId());
            intent.putExtra("perfilId", perfilId);
            launcherAdicionarExercicio.launch(intent);
        });
    }

    private void editarExercicio(Exercicio exercicio, int posicao) {
        Intent intent = new Intent(this, EditarExercicioActivity.class);
        intent.putExtra("exercicio", exercicio);
        intent.putExtra("posicao", posicao);
        intent.putExtra("perfilId", perfilId);
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

    private void recarregarLista() {
        listaExercicios.clear();
        listaExercicios.addAll(dbHelper.listarExerciciosPorTreino(treinoSelecionado.getId(), perfilId));
        adapter.notifyDataSetChanged();
    }
}

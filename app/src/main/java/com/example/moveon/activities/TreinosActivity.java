package com.example.moveon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;
import com.example.moveon.adapters.TreinoAdapter;
import com.example.moveon.models.Exercicio;
import com.example.moveon.models.Treino;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TreinosActivity extends AppCompatActivity {

    private RecyclerView recyclerTreinos;
    private TreinoAdapter treinoAdapter;
    private ArrayList<Treino> listaTreinos;

    private ImageButton btnAnotacoes, btnHistorico;
    private CalendarView calendarView;
    private FloatingActionButton btnAdicionarTreino;

    private ImageView imgPerfilTopo;
    private TextView txtNomePerfil;

    private static final int REQUEST_ADICIONAR_TREINO = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treinos);

        // Inicialização do perfil
        imgPerfilTopo = findViewById(R.id.imgPerfilTopo);
        txtNomePerfil = findViewById(R.id.txtNomePerfil);

        String nome = getIntent().getStringExtra("nomePerfil");
        int imagemRes = getIntent().getIntExtra("imgPerfil", R.drawable.ic_user);
        txtNomePerfil.setText("Olá, " + (nome != null ? nome : "Usuário"));
        imgPerfilTopo.setImageResource(imagemRes);

        // Componentes principais
        recyclerTreinos = findViewById(R.id.recyclerTreinos);
        btnAnotacoes = findViewById(R.id.imageButton_Historico);
        btnHistorico = findViewById(R.id.imageButton_Historico2);
        calendarView = findViewById(R.id.calendarView);
        btnAdicionarTreino = findViewById(R.id.btnAdicionarTreino);

        // Lista de treinos
        listaTreinos = new ArrayList<>();

        // PEITO
        ArrayList<Exercicio> exerciciosPeito = new ArrayList<>();
        exerciciosPeito.add(new Exercicio("Supino Reto", 4, 40, 12));
        exerciciosPeito.add(new Exercicio("Crucifixo Inclinado", 4, 20, 12));
        exerciciosPeito.add(new Exercicio("Flexão", 3, 0, 15));
        Treino peito = new Treino("Peito", R.drawable.ic_peito, 4, 12);
        peito.setListaExercicios(exerciciosPeito);
        listaTreinos.add(peito);

        // COSTAS
        ArrayList<Exercicio> exerciciosCostas = new ArrayList<>();
        exerciciosCostas.add(new Exercicio("Puxada na frente", 4, 35, 10));
        exerciciosCostas.add(new Exercicio("Remada curvada", 4, 30, 10));
        Treino costas = new Treino("Costas", R.drawable.ic_costas, 4, 10);
        costas.setListaExercicios(exerciciosCostas);
        listaTreinos.add(costas);

        // PERNA
        ArrayList<Exercicio> exerciciosPerna = new ArrayList<>();
        exerciciosPerna.add(new Exercicio("Agachamento", 4, 50, 12));
        exerciciosPerna.add(new Exercicio("Leg Press", 4, 120, 15));
        Treino perna = new Treino("Perna", R.drawable.ic_perna, 4, 15);
        perna.setListaExercicios(exerciciosPerna);
        listaTreinos.add(perna);

        // BRAÇO
        ArrayList<Exercicio> exerciciosBraco = new ArrayList<>();
        exerciciosBraco.add(new Exercicio("Rosca Direta", 3, 25, 10));
        exerciciosBraco.add(new Exercicio("Tríceps Testa", 3, 15, 12));
        Treino braco = new Treino("Braço", R.drawable.ic_braco, 3, 10);
        braco.setListaExercicios(exerciciosBraco);
        listaTreinos.add(braco);

        // Configuração do RecyclerView
        treinoAdapter = new TreinoAdapter(listaTreinos, this);
        recyclerTreinos.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        recyclerTreinos.setAdapter(treinoAdapter);

        // Botões de navegação
        btnAnotacoes.setOnClickListener(v -> {
            startActivity(new Intent(this, AnotacoesActivity.class));
        });

        btnHistorico.setOnClickListener(v -> {
            // TODO: Implementar tela de histórico
        });

        btnAdicionarTreino.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdicionarExercicioActivity.class);
            startActivityForResult(intent, REQUEST_ADICIONAR_TREINO);
        });

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // TODO: Implementar lógica de treinos por data
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADICIONAR_TREINO && resultCode == RESULT_OK && data != null) {
            String nome = data.getStringExtra("nome");
            int imagem = data.getIntExtra("imagem", R.drawable.ic_peito);
            Treino novoTreino = new Treino(nome, imagem);
            novoTreino.setListaExercicios(new ArrayList<>()); // Começa vazio
            listaTreinos.add(novoTreino);
            treinoAdapter.notifyItemInserted(listaTreinos.size() - 1);
        }
    }
}

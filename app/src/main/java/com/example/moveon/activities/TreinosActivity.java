package com.example.moveon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;
import com.example.moveon.adapters.TreinoAdapter;
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

    private static final int REQUEST_ADICIONAR_TREINO = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treinos);

        // Inicializações de componentes
        recyclerTreinos = findViewById(R.id.recyclerTreinos);
        btnAnotacoes = findViewById(R.id.imageButton_Historico);
        btnHistorico = findViewById(R.id.imageButton_Historico2);
        calendarView = findViewById(R.id.calendarView);
        btnAdicionarTreino = findViewById(R.id.btnAdicionarTreino);

        // Inicializar lista
        listaTreinos = new ArrayList<>();
        listaTreinos.add(new Treino("Peito", R.drawable.ic_peito));
        listaTreinos.add(new Treino("Costas", R.drawable.ic_costas));
        listaTreinos.add(new Treino("Perna", R.drawable.ic_perna));
        listaTreinos.add(new Treino("Braço", R.drawable.ic_braco));

        // Configurar RecyclerView
        treinoAdapter = new TreinoAdapter(listaTreinos, this);
        recyclerTreinos.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        recyclerTreinos.setAdapter(treinoAdapter);

        // Clique no botão de Anotações
        btnAnotacoes.setOnClickListener(v -> {
            Intent intent = new Intent(TreinosActivity.this, AnotacoesActivity.class);
            startActivity(intent);
        });

        // Clique no botão de Histórico
        btnHistorico.setOnClickListener(v -> {
            // TODO: Implementar tela de histórico
        });

        // Clique no botão flutuante para adicionar treino
        btnAdicionarTreino.setOnClickListener(v -> {
            Intent intent = new Intent(TreinosActivity.this, AdicionarTreinoActivity.class);
            startActivityForResult(intent, REQUEST_ADICIONAR_TREINO);
        });


        // Clique no calendário (pode ser customizado)
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // TODO: Lógica futura para treinos por data
        });
    }

    // Receber novo treino vindo da tela de AdicionarTreino
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADICIONAR_TREINO && resultCode == RESULT_OK && data != null) {
            String nome = data.getStringExtra("nome");
            int imagem = data.getIntExtra("imagem", R.drawable.ic_peito);

            listaTreinos.add(new Treino(nome, imagem));
            treinoAdapter.notifyItemInserted(listaTreinos.size() - 1);
        }
    }
}

package com.example.moveon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;
import com.example.moveon.adapters.AnotacaoAdapter;
import com.example.moveon.database.AnotacoesDBHelper;
import com.example.moveon.models.Anotacao;

import java.util.ArrayList;

public class AnotacoesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageButton btnNovaAnotacao;
    private AnotacoesDBHelper dbHelper;
    private AnotacaoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacoes);

        recyclerView = findViewById(R.id.recyclerViewAnotacoes);
        btnNovaAnotacao = findViewById(R.id.btnNovaAnotacao);

        dbHelper = new AnotacoesDBHelper(this);
        adapter = new AnotacaoAdapter(dbHelper.getTodasAnotacoes(), this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnNovaAnotacao.setOnClickListener(v -> {
            Intent intent = new Intent(this, NovaAnotacaoActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.atualizarLista(dbHelper.getTodasAnotacoes());
    }

    public void excluirAnotacao(int id) {
        dbHelper.excluirAnotacao(id);
        adapter.atualizarLista(dbHelper.getTodasAnotacoes());
    }
}

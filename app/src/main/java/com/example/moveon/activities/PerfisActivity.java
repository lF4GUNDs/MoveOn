package com.example.moveon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;
import com.example.moveon.adapters.PerfilAdapter;
import com.example.moveon.models.Perfil;
import com.example.moveon.utils.PerfilStorage;

import java.util.ArrayList;

public class PerfisActivity extends AppCompatActivity {

    private RecyclerView recyclerPerfis;
    private ArrayList<Perfil> listaPerfis;
    private PerfilAdapter adapter;
    private Button btnAdicionarPerfil;
    private TextView txtSemPerfis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfis);

        recyclerPerfis = findViewById(R.id.recyclerPerfis);
        btnAdicionarPerfil = findViewById(R.id.btnAdicionarPerfil);
        txtSemPerfis = findViewById(R.id.txtSemPerfis); // Mensagem de "nenhum perfil"

        recyclerPerfis.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        btnAdicionarPerfil.setOnClickListener(v -> {
            Intent i = new Intent(PerfisActivity.this, CadastroPerfilActivity.class);
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Recarrega os perfis toda vez que volta para essa tela
        listaPerfis = PerfilStorage.obterPerfis(this);

        adapter = new PerfilAdapter(listaPerfis, this, perfil -> {
            Intent i = new Intent(PerfisActivity.this, TreinosActivity.class);
            i.putExtra("nomePerfil", perfil.getNome());
            i.putExtra("imgPerfil", perfil.getImagemResId());
            startActivity(i);
        });

        recyclerPerfis.setAdapter(adapter);

        // Exibe mensagem se não houver perfis
        if (listaPerfis.isEmpty()) {
            txtSemPerfis.setVisibility(View.VISIBLE);
            recyclerPerfis.setVisibility(View.GONE);
        } else {
            txtSemPerfis.setVisibility(View.GONE);
            recyclerPerfis.setVisibility(View.VISIBLE);
        }
    }
}

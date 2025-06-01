package com.example.moveon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;
import com.example.moveon.adapters.ImagemAdapter;
import com.example.moveon.models.Perfil;
import com.example.moveon.utils.PerfilStorage;

import java.util.List;

public class CadastroPerfilActivity extends AppCompatActivity {

    private EditText editNome;
    private RecyclerView recyclerImagens;
    private Button btnSalvarPerfil;
    private int imagemSelecionada = R.drawable.ic_user; // imagem padrão

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_perfil);

        editNome = findViewById(R.id.editNome);
        recyclerImagens = findViewById(R.id.recyclerImagens);
        btnSalvarPerfil = findViewById(R.id.btnSalvarPerfil);

        int[] imagens = {
                R.drawable.ic_user,
                R.drawable.ic_perfil1,
                R.drawable.ic_perfil2,
                R.drawable.ic_perfil3,
                R.drawable.ic_perfil4,
                R.drawable.ic_perfil5,
                R.drawable.ic_perfil6,
                R.drawable.ic_perfil7,



        };

        ImagemAdapter adapter = new ImagemAdapter(this, imagens, imagemId -> imagemSelecionada = imagemId);
        recyclerImagens.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerImagens.setAdapter(adapter);

        btnSalvarPerfil.setOnClickListener(v -> {
            String nome = editNome.getText().toString().trim();
            if (!nome.isEmpty()) {
                List<Perfil> perfisExistentes = PerfilStorage.obterPerfis(this);

                // Novo ID = maior ID + 1
                int novoId = 1;
                for (Perfil p : perfisExistentes) {
                    if (p.getId() >= novoId) {
                        novoId = p.getId() + 1;
                    }
                }

                Perfil novoPerfil = new Perfil(novoId, nome, imagemSelecionada);
                PerfilStorage.salvarPerfil(this, novoPerfil);

                // Retorna para a tela de perfis
                Intent intent = new Intent(CadastroPerfilActivity.this, PerfisActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

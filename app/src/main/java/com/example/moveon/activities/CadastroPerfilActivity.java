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
                R.drawable.ic_user,
        };

        ImagemAdapter adapter = new ImagemAdapter(this, imagens, imagemId -> imagemSelecionada = imagemId);
        recyclerImagens.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerImagens.setAdapter(adapter);

        btnSalvarPerfil.setOnClickListener(v -> {
            String nome = editNome.getText().toString().trim();
            if (!nome.isEmpty()) {
                Perfil perfil = new Perfil(nome, imagemSelecionada);

                // ✅ Salvando perfil com SharedPreferences via PerfilStorage
                PerfilStorage.salvarPerfil(CadastroPerfilActivity.this, perfil);

                // Redirecionar de volta para a lista de perfis
                Intent intent = new Intent(CadastroPerfilActivity.this, PerfisActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

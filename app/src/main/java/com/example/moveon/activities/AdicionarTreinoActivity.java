package com.example.moveon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;
import com.example.moveon.adapters.ImagemAdapter;

public class AdicionarTreinoActivity extends AppCompatActivity {

    EditText editNome;
    ImageView imagemSelecionada;
    RecyclerView recyclerImagens;
    Button btnSalvar;

    int imagemAtual = R.drawable.ic_perna;

    int[] imagens = {
            R.drawable.ic_perna,
            R.drawable.ic_braco,
            R.drawable.ic_costas,
            R.drawable.ic_peito
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_treino);

        editNome = findViewById(R.id.editNome);
        imagemSelecionada = findViewById(R.id.imagemSelecionada);
        recyclerImagens = findViewById(R.id.recyclerImagens);
        btnSalvar = findViewById(R.id.btnSalvar);

        // Configurar RecyclerView de imagens
        recyclerImagens.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerImagens.setAdapter(new ImagemAdapter(this, imagens, resId -> {
            imagemAtual = resId;
            imagemSelecionada.setImageResource(resId);
        }));

        btnSalvar.setOnClickListener(v -> {
            String nome = editNome.getText().toString().trim();

            if (nome.isEmpty()) {
                Toast.makeText(this, "Informe o nome do treino", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent result = new Intent();
            result.putExtra("nome", nome);
            result.putExtra("imagem", imagemAtual);
            setResult(RESULT_OK, result);
            finish();
        });
    }
}

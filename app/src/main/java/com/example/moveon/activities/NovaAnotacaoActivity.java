package com.example.moveon.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moveon.R;
import com.example.moveon.database.AnotacoesDBHelper;
import com.example.moveon.models.Anotacao;

public class NovaAnotacaoActivity extends AppCompatActivity {

    private EditText editTitulo, editConteudo;
    private Button btnSalvar;
    private AnotacoesDBHelper dbHelper;
    private int idAnotacao = -1; // -1 = nova anotação

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_anotacao);

        editTitulo = findViewById(R.id.editTitulo);
        editConteudo = findViewById(R.id.editConteudo);
        btnSalvar = findViewById(R.id.btnSalvar);
        dbHelper = new AnotacoesDBHelper(this);

        // Verifica se veio ID para edição
        if (getIntent().hasExtra("id")) {
            idAnotacao = getIntent().getIntExtra("id", -1);
            Anotacao anotacao = dbHelper.getAnotacaoPorId(idAnotacao);
            if (anotacao != null) {
                editTitulo.setText(anotacao.getTitulo());
                editConteudo.setText(anotacao.getConteudo());
            }
        }

        btnSalvar.setOnClickListener(v -> {
            String titulo = editTitulo.getText().toString().trim();
            String conteudo = editConteudo.getText().toString().trim();

            if (titulo.isEmpty()) {
                Toast.makeText(this, "Digite um título", Toast.LENGTH_SHORT).show();
                return;
            }

            if (idAnotacao == -1) {
                dbHelper.salvarAnotacao(titulo, conteudo);
                Toast.makeText(this, "Anotação salva", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.atualizarAnotacao(idAnotacao, titulo, conteudo);
                Toast.makeText(this, "Anotação atualizada", Toast.LENGTH_SHORT).show();
            }

            finish(); // Volta para a tela de lista
        });
    }
}

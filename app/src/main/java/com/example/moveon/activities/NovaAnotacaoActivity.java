package com.example.moveon.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.moveon.R;
import com.example.moveon.database.AnotacoesDBHelper;

public class NovaAnotacaoActivity extends AppCompatActivity {

    private EditText editTitulo, editConteudo;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_anotacao);

        editTitulo = findViewById(R.id.editTitulo);
        editConteudo = findViewById(R.id.editConteudo);
        btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(v -> {
            String titulo = editTitulo.getText().toString();
            String conteudo = editConteudo.getText().toString();

            if (!titulo.isEmpty() && !conteudo.isEmpty()) {
                AnotacoesDBHelper dbHelper = new AnotacoesDBHelper(this);
                dbHelper.salvarAnotacao(titulo, conteudo);
                Toast.makeText(this, "Anotação salva!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

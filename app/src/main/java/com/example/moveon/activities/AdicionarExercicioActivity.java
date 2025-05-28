package com.example.moveon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moveon.R;
import com.example.moveon.models.Exercicio;

public class AdicionarExercicioActivity extends AppCompatActivity {

    private EditText editNome, editSeries, editRepeticoes, editPeso;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_exercicio);

        editNome = findViewById(R.id.editNome);
        editSeries = findViewById(R.id.editSeries);
        editRepeticoes = findViewById(R.id.editRepeticoes);
        editPeso = findViewById(R.id.editPeso);
        btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(v -> {
            String nome = editNome.getText().toString().trim();
            String seriesStr = editSeries.getText().toString().trim();
            String repeticoesStr = editRepeticoes.getText().toString().trim();
            String pesoStr = editPeso.getText().toString().trim();

            if (nome.isEmpty() || seriesStr.isEmpty() || repeticoesStr.isEmpty() || pesoStr.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int series = Integer.parseInt(seriesStr);
            int repeticoes = Integer.parseInt(repeticoesStr);
            int peso = Integer.parseInt(pesoStr);

            Exercicio novoExercicio = new Exercicio(nome, series, repeticoes, peso);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("exercicio", novoExercicio);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}

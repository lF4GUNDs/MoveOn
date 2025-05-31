package com.example.moveon.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moveon.R;
import com.example.moveon.database.ExerciciosDBHelper;
import com.example.moveon.models.Exercicio;

public class AdicionarExercicioActivity extends AppCompatActivity {

    private EditText editNome, editSeries, editRepeticoes, editPeso;
    private Button btnSalvar;
    private ExerciciosDBHelper dbHelper;
    private int treinoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_exercicio);

        editNome = findViewById(R.id.editNome);
        editSeries = findViewById(R.id.editSeries);
        editRepeticoes = findViewById(R.id.editRepeticoes);
        editPeso = findViewById(R.id.editPeso);
        btnSalvar = findViewById(R.id.btnSalvar);

        dbHelper = new ExerciciosDBHelper(this);

        if (getIntent() != null && getIntent().hasExtra("treinoId")) {
            treinoId = getIntent().getIntExtra("treinoId", -1);
        }

        btnSalvar.setOnClickListener(v -> {
            String nome = editNome.getText().toString().trim();
            String seriesStr = editSeries.getText().toString().trim();
            String repeticoesStr = editRepeticoes.getText().toString().trim();
            String pesoStr = editPeso.getText().toString().trim();

            if (nome.isEmpty() || seriesStr.isEmpty() || repeticoesStr.isEmpty() || pesoStr.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int series = Integer.parseInt(seriesStr);
                int repeticoes = Integer.parseInt(repeticoesStr);
                float peso = Float.parseFloat(pesoStr);

                Exercicio novoExercicio = new Exercicio(nome, series, peso, repeticoes);
                long id = dbHelper.adicionarExercicio(novoExercicio, treinoId);

                if (id != -1) {
                    Toast.makeText(this, "Exercício adicionado com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Erro ao adicionar exercício", Toast.LENGTH_SHORT).show();
                }

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Valores inválidos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

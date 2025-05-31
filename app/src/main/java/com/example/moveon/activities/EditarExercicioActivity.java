package com.example.moveon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moveon.R;
import com.example.moveon.database.ExerciciosDBHelper;
import com.example.moveon.models.Exercicio;

public class EditarExercicioActivity extends AppCompatActivity {

    private EditText editNome, editSeries, editRepeticoes, editPeso;
    private Button btnSalvar;

    private int posicao = -1;
    private int exercicioId = -1;
    private ExerciciosDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_exercicio);

        inicializarComponentes();
        configurarDadosRecebidos();
        configurarBotaoSalvar();
    }

    private void inicializarComponentes() {
        editNome = findViewById(R.id.editNome);
        editSeries = findViewById(R.id.editSeries);
        editRepeticoes = findViewById(R.id.editRepeticoes);
        editPeso = findViewById(R.id.editPeso);
        btnSalvar = findViewById(R.id.btnSalvar);
        dbHelper = new ExerciciosDBHelper(this);
    }

    private void configurarDadosRecebidos() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("exercicio")) {
            Exercicio exercicio = (Exercicio) intent.getSerializableExtra("exercicio");
            posicao = intent.getIntExtra("posicao", -1);
            exercicioId = exercicio.getId();

            editNome.setText(exercicio.getNome());
            editSeries.setText(String.valueOf(exercicio.getSeries()));
            editRepeticoes.setText(String.valueOf(exercicio.getReps()));
            editPeso.setText(String.valueOf(exercicio.getPeso()));
        } else {
            Toast.makeText(this, "Erro ao carregar exercício", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void configurarBotaoSalvar() {
        btnSalvar.setOnClickListener(v -> {
            String nome = editNome.getText().toString().trim();
            String sSeries = editSeries.getText().toString().trim();
            String sReps = editRepeticoes.getText().toString().trim();
            String sPeso = editPeso.getText().toString().trim();

            if (nome.isEmpty() || sSeries.isEmpty() || sReps.isEmpty() || sPeso.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int series = Integer.parseInt(sSeries);
                int reps = Integer.parseInt(sReps);
                float peso = Float.parseFloat(sPeso);

                Exercicio exercicioAtualizado = new Exercicio(nome, series, peso, reps);
                exercicioAtualizado.setId(exercicioId);

                int linhasAfetadas = dbHelper.atualizarExercicio(exercicioId, exercicioAtualizado);

                if (linhasAfetadas > 0) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("exercicio", exercicioAtualizado);
                    resultIntent.putExtra("posicao", posicao);
                    setResult(RESULT_OK, resultIntent);
                    Toast.makeText(this, "Exercício atualizado com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Erro ao atualizar exercício", Toast.LENGTH_SHORT).show();
                }

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Valores inválidos: verifique os campos numéricos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

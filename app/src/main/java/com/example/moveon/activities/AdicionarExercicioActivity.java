package com.example.moveon.activities;

import android.os.Bundle;
import android.util.Log;
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
    private int perfilId = -1;

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

        if (getIntent() != null) {
            treinoId = getIntent().getIntExtra("treinoId", -1);
            perfilId = getIntent().getIntExtra("perfilId", -1);
            Log.d("DEBUG", "treinoId: " + treinoId + ", perfilId: " + perfilId);
        }

        if (treinoId == -1 || perfilId == -1) {
            Toast.makeText(this, "Dados inválidos do treino ou perfil", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnSalvar.setOnClickListener(v -> salvarExercicio());
    }

    private void salvarExercicio() {
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
            long id = dbHelper.adicionarExercicio(novoExercicio, treinoId, perfilId);

            if (id != -1) {
                Toast.makeText(this, "Exercício adicionado com sucesso", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Erro ao adicionar exercício", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valores inválidos", Toast.LENGTH_SHORT).show();
        }
    }
}

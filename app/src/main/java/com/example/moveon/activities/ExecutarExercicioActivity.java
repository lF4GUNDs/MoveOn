package com.example.moveon.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moveon.R;
import com.example.moveon.database.ExecucaoExercicioDBHelper;
import com.example.moveon.models.Exercicio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ExecutarExercicioActivity extends AppCompatActivity {

    private TextView txtNome, txtSeriesInfo, txtContador, txtStatus;
    private TextView btnCompletarSerie;
    private Chronometer cronometroTreino;

    private int serieAtual = 1;
    private int indiceExercicioAtual = 0;
    private Exercicio exercicio;
    private ArrayList<Exercicio> lista;

    private ExecucaoExercicioDBHelper dbHelper;
    private int perfilId = -1;
    private final int tempoDescanso = 60; // segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executar_exercicio);

        txtNome = findViewById(R.id.txtNomeExercicio);
        txtSeriesInfo = findViewById(R.id.txtSeriesInfo);
        txtContador = findViewById(R.id.txtContador);
        txtStatus = findViewById(R.id.tvDescanso);
        btnCompletarSerie = findViewById(R.id.btnCompletarSerie);
        cronometroTreino = findViewById(R.id.cronometroTreino);

        dbHelper = new ExecucaoExercicioDBHelper(this);
        perfilId = getIntent().getIntExtra("perfilId", -1);
        lista = (ArrayList<Exercicio>) getIntent().getSerializableExtra("listaExercicios");

        if (perfilId == -1 || lista == null || lista.isEmpty()) {
            Toast.makeText(this, "Dados inválidos para execução", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        cronometroTreino.setBase(SystemClock.elapsedRealtime());
        cronometroTreino.start();

        iniciarExercicio(indiceExercicioAtual);

        btnCompletarSerie.setOnClickListener(v -> {
            salvarExecucaoAtual();
            iniciarDescanso();
        });
    }

    private void iniciarExercicio(int index) {
        exercicio = lista.get(index);
        serieAtual = 1;
        atualizarUI();
        txtStatus.setText("Executando exercício...");
    }

    private void iniciarProximoExercicio() {
        if (indiceExercicioAtual < lista.size()) {
            iniciarExercicio(indiceExercicioAtual);
        } else {
            cronometroTreino.stop();
            Toast.makeText(this, "🏁 Treino finalizado!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void atualizarUI() {
        txtNome.setText(exercicio.getNome());
        txtSeriesInfo.setText("Série " + serieAtual + " de " + exercicio.getSeries());
        txtContador.setText("Peso: " + exercicio.getPeso() + "kg | Reps: " + exercicio.getReps());
    }

    private void salvarExecucaoAtual() {
        String dataAtual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        dbHelper.salvarExecucao(
                perfilId,
                dataAtual,
                exercicio.getNome(),
                serieAtual,
                exercicio.getReps(),
                exercicio.getPeso(),
                tempoDescanso
        );
    }

    private void iniciarDescanso() {
        btnCompletarSerie.setEnabled(false);
        txtStatus.setText("Descanso: " + tempoDescanso + "s");

        new CountDownTimer(tempoDescanso * 1000L, 1000) {
            public void onTick(long millisUntilFinished) {
                txtStatus.setText("Descanso: " + (millisUntilFinished / 1000) + "s");
            }

            public void onFinish() {
                serieAtual++;
                if (serieAtual > exercicio.getSeries()) {
                    indiceExercicioAtual++;
                    iniciarProximoExercicio();
                } else {
                    atualizarUI();
                    txtStatus.setText("Executando exercício...");
                }
                btnCompletarSerie.setEnabled(true);
            }
        }.start();
    }
}

package com.example.moveon.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moveon.R;
import com.example.moveon.database.ExecucaoExercicioDBHelper;
import com.example.moveon.models.Exercicio;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExecutarExercicioActivity extends AppCompatActivity {

    private TextView tvSerie, tvPeso, tvReps, tvDescanso, tvStatus;
    private Button btnCompletarSerie;

    private int serieAtual = 1;
    private int totalSeries, peso, reps, descansoSegundos = 60;
    private String nomeExercicio;
    private int perfilId = 1; // Ajustar para perfil logado

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executar_exercicio);

        tvSerie = findViewById(R.id.tvSerieAtual);
        tvPeso = findViewById(R.id.tvPeso);
        tvReps = findViewById(R.id.tvReps);
        tvDescanso = findViewById(R.id.tvDescanso);
        tvStatus = findViewById(R.id.tvStatus);
        btnCompletarSerie = findViewById(R.id.btnCompletarSerie);

        // Recuperar exercício vindo da intent
        Exercicio exercicio = getIntent().getParcelableExtra("exercicio");

        if (exercicio != null) {
            nomeExercicio = exercicio.getNome();
            totalSeries = exercicio.getSeries();
            peso = exercicio.getPeso();
            reps = exercicio.getReps();
        } else {
            // fallback padrão
            nomeExercicio = "Exercício Desconhecido";
            totalSeries = 4;
            peso = 30;
            reps = 12;
        }

        tvPeso.setText("Peso: " + peso + " kg");
        tvReps.setText("Reps: " + reps);
        atualizarUI();

        btnCompletarSerie.setOnClickListener(v -> {
            salvarExecucaoNoBanco();
            if (serieAtual < totalSeries) {
                iniciarDescanso();
            } else {
                tvStatus.setText("Treino finalizado!");
                btnCompletarSerie.setEnabled(false);
            }
        });
    }

    private void atualizarUI() {
        tvSerie.setText("Série: " + serieAtual + "/" + totalSeries);
        tvDescanso.setText("");
        tvStatus.setText("Executando exercício...");
    }

    private void iniciarDescanso() {
        tvStatus.setText("Descanso...");
        btnCompletarSerie.setEnabled(false);

        timer = new CountDownTimer(descansoSegundos * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvDescanso.setText("Descanso: " + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                serieAtual++;
                atualizarUI();
                btnCompletarSerie.setEnabled(true);
            }
        }.start();
    }

    private void salvarExecucaoNoBanco() {
        ExecucaoExercicioDBHelper dbHelper = new ExecucaoExercicioDBHelper(this);
        String dataHoje = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        dbHelper.salvarExecucao(
                perfilId,
                dataHoje,
                nomeExercicio,
                serieAtual,
                peso,
                reps,
                descansoSegundos
        );

        Toast.makeText(this, "Série " + serieAtual + " salva!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        super.onDestroy();
    }
}

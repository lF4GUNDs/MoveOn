package com.example.moveon.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moveon.R;
import com.example.moveon.database.ExecucaoExercicioDBHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HistoricoActivity extends AppCompatActivity {

    private BarChart barChart;
    private TextView txtResumo;
    private Button btnLimpar;
    private int perfilId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        barChart = findViewById(R.id.barChart);
        txtResumo = findViewById(R.id.txtResumoHistorico);
        btnLimpar = findViewById(R.id.btnLimparHistorico);

        perfilId = getIntent().getIntExtra("perfilId", -1);
        if (perfilId == -1) {
            finish();
            return;
        }

        ExecucaoExercicioDBHelper dbHelper = new ExecucaoExercicioDBHelper(this);
        atualizarGraficoEResumo(dbHelper);

        btnLimpar.setOnClickListener(v -> {
            new AlertDialog.Builder(HistoricoActivity.this)
                    .setTitle("Limpar Histórico")
                    .setMessage("Tem certeza que deseja apagar todo o histórico deste perfil?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        dbHelper.limparHistoricoPorPerfil(perfilId);
                        Toast.makeText(HistoricoActivity.this, "Histórico apagado!", Toast.LENGTH_SHORT).show();
                        atualizarGraficoEResumo(dbHelper);
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    private void atualizarGraficoEResumo(ExecucaoExercicioDBHelper dbHelper) {
        Map<String, Integer> dadosGrupo = dbHelper.getSeriesPorGrupoMuscular(perfilId);
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        int index = 0;

        for (Map.Entry<String, Integer> entry : dadosGrupo.entrySet()) {
            entries.add(new BarEntry(index, entry.getValue()));
            labels.add(entry.getKey());
            index++;
        }

        BarDataSet dataSet = new BarDataSet(entries, "Séries por Grupo Muscular");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(12f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        Description desc = new Description();
        desc.setText("Histórico de Séries");
        desc.setTextColor(Color.LTGRAY);
        desc.setTextSize(12f);
        barChart.setDescription(desc);

        barChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int i = (int) value;
                return (i >= 0 && i < labels.size()) ? labels.get(i) : "";
            }
        });

        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setTextColor(Color.WHITE);
        barChart.getAxisLeft().setTextColor(Color.WHITE);
        barChart.getAxisRight().setTextColor(Color.WHITE);
        barChart.getLegend().setTextColor(Color.WHITE);
        barChart.setFitBars(true);
        barChart.animateY(1000);

        // ❌ Desativa interações no gráfico
        barChart.setScaleEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setTouchEnabled(false);
        barChart.setDragEnabled(false);
        barChart.setHighlightPerTapEnabled(false);
        barChart.setHighlightPerDragEnabled(false);

        barChart.invalidate();

        // Resumo
        int totalSeries = dbHelper.getTotalSeries(perfilId);
        int totalSessoes = dbHelper.getTotalSessoes(perfilId);
        int tempoTotalSegundos = dbHelper.getDuracaoTotalEmSegundos(perfilId);

        String tempoFormatado = formatarTempo(tempoTotalSegundos);
        String resumo = "Tempo total: " + tempoFormatado +
                "\nSéries: " + totalSeries +
                "\nSessões: " + totalSessoes;

        txtResumo.setText(resumo);
    }

    private String formatarTempo(int segundosTotais) {
        int horas = segundosTotais / 3600;
        int minutos = (segundosTotais % 3600) / 60;
        int segundos = segundosTotais % 60;
        return String.format(Locale.getDefault(), "%02dh %02dm %02ds", horas, minutos, segundos);
    }
}

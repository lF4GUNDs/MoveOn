package com.example.moveon.activities;

import android.graphics.Color;
import android.os.Bundle;

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
import java.util.Map;

public class HistoricoActivity extends AppCompatActivity {

    private BarChart barChart;
    private int perfilId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        barChart = findViewById(R.id.barChart);
        perfilId = getIntent().getIntExtra("perfilId", -1);

        if (perfilId == -1) {
            finish();
            return;
        }

        ExecucaoExercicioDBHelper dbHelper = new ExecucaoExercicioDBHelper(this);
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

        // ✅ Corrigido: ValueFormatter usando classe anônima (sem lambda)
        barChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int i = (int) value;
                if (i >= 0 && i < labels.size()) {
                    return labels.get(i);
                }
                return "";
            }
        });

        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setTextColor(Color.WHITE);
        barChart.getAxisLeft().setTextColor(Color.WHITE);
        barChart.getAxisRight().setTextColor(Color.WHITE);
        barChart.getLegend().setTextColor(Color.WHITE);
        barChart.setFitBars(true);
        barChart.animateY(1000);
        barChart.invalidate();
    }
}

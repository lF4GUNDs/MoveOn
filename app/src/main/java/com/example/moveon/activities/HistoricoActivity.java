package com.example.moveon.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;
import com.example.moveon.adapters.HistoricoAdapter;
import com.example.moveon.database.ExecucaoExercicioDBHelper;
import com.example.moveon.models.HistoricoTreino;

import java.util.List;

public class HistoricoActivity extends AppCompatActivity {

    private RecyclerView recyclerHistorico;
    private ExecucaoExercicioDBHelper dbHelper;
    private int perfilId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        perfilId = getIntent().getIntExtra("perfilId", -1);
        recyclerHistorico = findViewById(R.id.recyclerHistorico);
        dbHelper = new ExecucaoExercicioDBHelper(this);

        if (perfilId == -1) {
            Toast.makeText(this, "Perfil inválido!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        List<HistoricoTreino> historico = dbHelper.buscarHistoricoTreinos(perfilId);

        recyclerHistorico.setLayoutManager(new LinearLayoutManager(this));
        recyclerHistorico.setAdapter(new HistoricoAdapter(historico));
    }
}

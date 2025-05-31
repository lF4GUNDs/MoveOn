package com.example.moveon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;
import com.example.moveon.adapters.TreinoAdapter;
import com.example.moveon.models.Treino;

import java.util.ArrayList;

public class TreinosActivity extends AppCompatActivity {

    private RecyclerView recyclerTreinos;
    private TreinoAdapter treinoAdapter;
    private ArrayList<Treino> listaTreinos;

    private ImageButton btnAnotacoes, btnHistorico;
    private CalendarView calendarView;

    private ImageView imgPerfilTopo;
    private TextView txtNomePerfil;

    private int perfilId; // ✅ NOVO campo

    private static final int REQUEST_ADICIONAR_TREINO = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treinos);

        imgPerfilTopo = findViewById(R.id.imgPerfilTopo);
        txtNomePerfil = findViewById(R.id.txtNomePerfil);

        // ✅ Recupera dados do perfil
        String nome = getIntent().getStringExtra("nomePerfil");
        int imagemRes = getIntent().getIntExtra("imgPerfil", R.drawable.ic_user);
        perfilId = getIntent().getIntExtra("perfilId", -1); // ✅ Obter perfilId

        if (perfilId == -1) {
            Toast.makeText(this, "Perfil inválido!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        txtNomePerfil.setText("Olá, " + (nome != null ? nome : "Usuário"));
        imgPerfilTopo.setImageResource(imagemRes);

        recyclerTreinos = findViewById(R.id.recyclerTreinos);
        btnAnotacoes = findViewById(R.id.imageButton_Historico);
        btnHistorico = findViewById(R.id.imageButton_Historico2);
        calendarView = findViewById(R.id.calendarView);

        listaTreinos = new ArrayList<>();

        // IDs fixos: 1 a 4
        adicionarTreinoPadrao(1, "Peito", R.drawable.ic_peito);
        adicionarTreinoPadrao(2, "Costas", R.drawable.ic_costas);
        adicionarTreinoPadrao(3, "Perna", R.drawable.ic_perna);
        adicionarTreinoPadrao(4, "Braço", R.drawable.ic_braco);

        // ✅ Passar perfilId para o adapter
        treinoAdapter = new TreinoAdapter(listaTreinos, this, perfilId);
        recyclerTreinos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerTreinos.setAdapter(treinoAdapter);

        btnAnotacoes.setOnClickListener(v -> startActivity(new Intent(this, AnotacoesActivity.class)));

        btnHistorico.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoricoActivity.class);
            intent.putExtra("perfilId", perfilId); // envia o perfil atual para a tela de histórico
            startActivity(intent);
        });


        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // TODO: Implementar lógica de treinos por data
        });
    }

    private void adicionarTreinoPadrao(int id, String nome, int imagem) {
        Treino treino = new Treino(id, nome, imagem, 4, 12);
        treino.setListaExercicios(new ArrayList<>());
        listaTreinos.add(treino);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADICIONAR_TREINO && resultCode == RESULT_OK && data != null) {
            String nome = data.getStringExtra("nome");
            int imagem = data.getIntExtra("imagem", R.drawable.ic_peito);

            if (nome != null && !nome.trim().isEmpty()) {
                nome = capitalize(nome.trim());

                boolean treinoJaExiste = false;
                for (Treino t : listaTreinos) {
                    if (t.getNome().equalsIgnoreCase(nome)) {
                        treinoJaExiste = true;
                        break;
                    }
                }

                if (!treinoJaExiste) {
                    int novoId = listaTreinos.size() + 1;
                    Treino novoTreino = new Treino(novoId, nome, imagem, 4, 12);
                    novoTreino.setListaExercicios(new ArrayList<>());
                    listaTreinos.add(novoTreino);
                    treinoAdapter.notifyItemInserted(listaTreinos.size() - 1);
                } else {
                    Toast.makeText(this, "Esse treino já foi adicionado!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String capitalize(String input) {
        if (input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}

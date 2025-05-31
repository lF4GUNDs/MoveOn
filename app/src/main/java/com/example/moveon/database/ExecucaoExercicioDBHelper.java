package com.example.moveon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moveon.models.HistoricoTreino;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecucaoExercicioDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moveon_execucao.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_NAME = "execucoes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PERFIL_ID = "perfil_id";
    public static final String COLUMN_EXERCICIO_ID = "exercicio_id";
    public static final String COLUMN_NOME_EXERCICIO = "nome_exercicio";
    public static final String COLUMN_SERIE_NUMERO = "serie_numero";
    public static final String COLUMN_REPS_FEITAS = "reps_feitas";
    public static final String COLUMN_PESO_USADO = "peso_usado";
    public static final String COLUMN_DESCANSO = "descanso_segundos";
    public static final String COLUMN_DATA_EXECUCAO = "data_execucao"; // formato ISO: yyyy-MM-dd HH:mm:ss

    public ExecucaoExercicioDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PERFIL_ID + " INTEGER NOT NULL, " +
                COLUMN_EXERCICIO_ID + " INTEGER NOT NULL, " +
                COLUMN_NOME_EXERCICIO + " TEXT NOT NULL, " +
                COLUMN_SERIE_NUMERO + " INTEGER NOT NULL, " +
                COLUMN_REPS_FEITAS + " INTEGER NOT NULL, " +
                COLUMN_PESO_USADO + " REAL NOT NULL, " +
                COLUMN_DESCANSO + " INTEGER NOT NULL, " +
                COLUMN_DATA_EXECUCAO + " TEXT NOT NULL" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long salvarExecucao(int perfilId, String dataHora, String nomeExercicio,
                               int serieNumero, int repsFeitas, float pesoUsado, int descansoSegundos) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PERFIL_ID, perfilId);
        values.put(COLUMN_EXERCICIO_ID, -1);
        values.put(COLUMN_NOME_EXERCICIO, nomeExercicio);
        values.put(COLUMN_SERIE_NUMERO, serieNumero);
        values.put(COLUMN_REPS_FEITAS, repsFeitas);
        values.put(COLUMN_PESO_USADO, pesoUsado);
        values.put(COLUMN_DESCANSO, descansoSegundos);
        values.put(COLUMN_DATA_EXECUCAO, dataHora);

        return db.insert(TABLE_NAME, null, values);
    }

    public ArrayList<String> listarExecucoesPorExercicio(int exercicioId) {
        ArrayList<String> execucoes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null,
                COLUMN_EXERCICIO_ID + " = ?", new String[]{String.valueOf(exercicioId)},
                null, null, COLUMN_DATA_EXECUCAO + " DESC");

        try {
            while (cursor.moveToNext()) {
                int serie = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SERIE_NUMERO));
                int reps = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REPS_FEITAS));
                float peso = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_PESO_USADO));
                int descanso = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DESCANSO));
                String data = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATA_EXECUCAO));

                String linha = "Série " + serie + ": " + reps + " reps com " + peso + "kg | Descanso: " + descanso + "s em " + data;
                execucoes.add(linha);
            }
        } finally {
            cursor.close();
        }

        return execucoes;
    }

    public List<HistoricoTreino> buscarHistoricoTreinos(int perfilId) {
        List<HistoricoTreino> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_DATA_EXECUCAO + " as data, " +
                COLUMN_NOME_EXERCICIO + " as nomeGrupoMuscular, " +
                "SUM(" + COLUMN_DESCANSO + ") AS duracao " +
                "FROM " + TABLE_NAME + " WHERE " + COLUMN_PERFIL_ID + " = ? " +
                "GROUP BY data, nomeGrupoMuscular ORDER BY data DESC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(perfilId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));
                String nomeGrupo = cursor.getString(cursor.getColumnIndexOrThrow("nomeGrupoMuscular"));
                int duracao = cursor.getInt(cursor.getColumnIndexOrThrow("duracao"));

                lista.add(new HistoricoTreino(data, nomeGrupo, duracao));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return lista;
    }

    // ✅ Novo método para gráfico de barras: séries por grupo muscular
    public Map<String, Integer> getSeriesPorGrupoMuscular(int perfilId) {
        Map<String, Integer> mapa = new HashMap<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT " + COLUMN_NOME_EXERCICIO + ", SUM(" + COLUMN_SERIE_NUMERO + ") AS total_series " +
                "FROM " + TABLE_NAME + " WHERE " + COLUMN_PERFIL_ID + " = ? " +
                "GROUP BY " + COLUMN_NOME_EXERCICIO;

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(perfilId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME_EXERCICIO)).toLowerCase();
                int series = cursor.getInt(cursor.getColumnIndexOrThrow("total_series"));

                String grupo;
                if (nome.contains("supino") || nome.contains("crucifixo")) {
                    grupo = "Peito";
                } else if (nome.contains("remada") || nome.contains("puxada") || nome.contains("costas") || nome.contains("terra")) {
                    grupo = "Costas";
                } else if (nome.contains("agachamento") || nome.contains("perna") || nome.contains("leg") || nome.contains("extensora")) {
                    grupo = "Perna";
                } else if (nome.contains("rosca") || nome.contains("tríceps") || nome.contains("triceps") || nome.contains("bíceps")) {
                    grupo = "Braço";
                } else {
                    grupo = "Outros";
                }

                int total = mapa.getOrDefault(grupo, 0);
                mapa.put(grupo, total + series);

            } while (cursor.moveToNext());
            cursor.close();
        }

        return mapa;
    }
}

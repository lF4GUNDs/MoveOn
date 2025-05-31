package com.example.moveon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moveon.models.Exercicio;

import java.util.ArrayList;
import java.util.HashMap;

public class ExerciciosDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moveon.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_NAME = "exercicios";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_SERIES = "series";
    public static final String COLUMN_REPETICOES = "repeticoes";
    public static final String COLUMN_PESO = "peso";
    public static final String COLUMN_TREINO_ID = "treino_id";
    public static final String COLUMN_PERFIL_ID = "perfil_id";

    public ExerciciosDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOME + " TEXT NOT NULL, " +
                COLUMN_SERIES + " INTEGER NOT NULL, " +
                COLUMN_REPETICOES + " INTEGER NOT NULL, " +
                COLUMN_PESO + " REAL NOT NULL, " +
                COLUMN_TREINO_ID + " INTEGER NOT NULL, " +
                COLUMN_PERFIL_ID + " INTEGER NOT NULL" +
                ");";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long adicionarExercicio(Exercicio e, int treinoId, int perfilId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, e.getNome());
        values.put(COLUMN_SERIES, e.getSeries());
        values.put(COLUMN_REPETICOES, e.getReps());
        values.put(COLUMN_PESO, e.getPeso());
        values.put(COLUMN_TREINO_ID, treinoId);
        values.put(COLUMN_PERFIL_ID, perfilId);
        return db.insert(TABLE_NAME, null, values);
    }

    public int atualizarExercicio(int id, Exercicio e) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, e.getNome());
        values.put(COLUMN_SERIES, e.getSeries());
        values.put(COLUMN_REPETICOES, e.getReps());
        values.put(COLUMN_PESO, e.getPeso());
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int excluirExercicio(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public ArrayList<Exercicio> listarExerciciosPorTreino(int treinoId, int perfilId) {
        ArrayList<Exercicio> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null,
                COLUMN_TREINO_ID + " = ? AND " + COLUMN_PERFIL_ID + " = ?",
                new String[]{String.valueOf(treinoId), String.valueOf(perfilId)},
                null, null, null);

        try {
            while (cursor.moveToNext()) {
                Exercicio e = new Exercicio(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SERIES)),
                        (float) cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PESO)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REPETICOES))
                );
                e.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                e.setTreinoId(treinoId);
                e.setPerfilId(perfilId);
                lista.add(e);
            }
        } finally {
            cursor.close();
        }

        return lista;
    }

    public void preencherExerciciosPadrao(int treinoId, String nomeTreino, int perfilId) {
        ArrayList<Exercicio> exerciciosPadrao = new ArrayList<>();

        switch (nomeTreino.toLowerCase()) {
            case "peito":
                exerciciosPadrao.add(new Exercicio("Supino Reto", 4, 30.0f, 10));
                exerciciosPadrao.add(new Exercicio("Supino Inclinado", 3, 25.0f, 12));
                exerciciosPadrao.add(new Exercicio("Crucifixo", 3, 15.0f, 15));
                break;
            case "costas":
                exerciciosPadrao.add(new Exercicio("Puxada Frente", 4, 40.0f, 10));
                exerciciosPadrao.add(new Exercicio("Remada Curvada", 4, 35.0f, 12));
                exerciciosPadrao.add(new Exercicio("Levantamento Terra", 3, 50.0f, 8));
                break;
            case "braço":
            case "braços":
                exerciciosPadrao.add(new Exercicio("Rosca Direta", 3, 12.0f, 12));
                exerciciosPadrao.add(new Exercicio("Tríceps Pulley", 3, 15.0f, 15));
                exerciciosPadrao.add(new Exercicio("Rosca Martelo", 3, 10.0f, 10));
                break;
            case "perna":
                exerciciosPadrao.add(new Exercicio("Agachamento Livre", 4, 50.0f, 10));
                exerciciosPadrao.add(new Exercicio("Leg Press", 4, 80.0f, 12));
                exerciciosPadrao.add(new Exercicio("Cadeira Extensora", 3, 25.0f, 15));
                break;
        }

        for (Exercicio e : exerciciosPadrao) {
            adicionarExercicio(e, treinoId, perfilId);
        }
    }

    // ✅ Método adicional para obter séries por grupo muscular
    public HashMap<String, Integer> getSeriesPorGrupoMuscular(int perfilId) {
        HashMap<String, Integer> mapa = new HashMap<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT " + COLUMN_NOME + ", SUM(" + COLUMN_SERIES + ") as total_series " +
                "FROM " + TABLE_NAME +
                " WHERE " + COLUMN_PERFIL_ID + " = ?" +
                " GROUP BY " + COLUMN_NOME;

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(perfilId)});

        try {
            while (cursor.moveToNext()) {
                String nomeGrupo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME));
                int totalSeries = cursor.getInt(cursor.getColumnIndexOrThrow("total_series"));

                if (mapa.containsKey(nomeGrupo)) {
                    mapa.put(nomeGrupo, mapa.get(nomeGrupo) + totalSeries);
                } else {
                    mapa.put(nomeGrupo, totalSeries);
                }
            }
        } finally {
            cursor.close();
        }

        return mapa;
    }
}

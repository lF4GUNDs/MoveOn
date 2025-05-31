package com.example.moveon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
        // Destroi a tabela anterior e recria (em produção, use ALTER TABLE com cautela)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // ✅ Novo método completo para salvar execução com perfil
    public long salvarExecucao(int perfilId, String dataHora, String nomeExercicio,
                               int serieNumero, int pesoUsado, int repsFeitas, int descansoSegundos) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PERFIL_ID, perfilId);
        values.put(COLUMN_EXERCICIO_ID, -1); // caso você não tenha o ID do exercício, pode definir -1
        values.put(COLUMN_NOME_EXERCICIO, nomeExercicio);
        values.put(COLUMN_SERIE_NUMERO, serieNumero);
        values.put(COLUMN_REPS_FEITAS, repsFeitas);
        values.put(COLUMN_PESO_USADO, pesoUsado);
        values.put(COLUMN_DESCANSO, descansoSegundos);
        values.put(COLUMN_DATA_EXECUCAO, dataHora);

        return db.insert(TABLE_NAME, null, values);
    }

    // ✅ Lista execuções para um exercício (por ID)
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
}

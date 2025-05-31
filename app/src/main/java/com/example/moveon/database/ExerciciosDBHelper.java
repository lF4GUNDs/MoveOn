package com.example.moveon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moveon.models.Exercicio;

import java.util.ArrayList;

public class ExerciciosDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moveon.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "exercicios";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_SERIES = "series";
    public static final String COLUMN_REPETICOES = "repeticoes";
    public static final String COLUMN_PESO = "peso";
    public static final String COLUMN_TREINO_ID = "treino_id";

    public ExerciciosDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Cria a tabela com os tipos adequados, peso como REAL para float
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOME + " TEXT NOT NULL, " +
                COLUMN_SERIES + " INTEGER NOT NULL, " +
                COLUMN_REPETICOES + " INTEGER NOT NULL, " +
                COLUMN_PESO + " REAL NOT NULL, " +
                COLUMN_TREINO_ID + " INTEGER NOT NULL" +
                ");";
        db.execSQL(createTable);
    }

    // Atualiza a tabela (descarta e recria, cuidado com dados)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insere um novo exercício, associando com treinoId
    public long adicionarExercicio(Exercicio e, int treinoId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, e.getNome());
        values.put(COLUMN_SERIES, e.getSeries());
        values.put(COLUMN_REPETICOES, e.getReps());
        values.put(COLUMN_PESO, e.getPeso());
        values.put(COLUMN_TREINO_ID, treinoId);
        return db.insert(TABLE_NAME, null, values);
    }

    // Atualiza exercício existente pelo ID
    public int atualizarExercicio(int id, Exercicio e) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, e.getNome());
        values.put(COLUMN_SERIES, e.getSeries());
        values.put(COLUMN_REPETICOES, e.getReps());
        values.put(COLUMN_PESO, e.getPeso());
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Remove exercício pelo ID
    public int excluirExercicio(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Lista todos os exercícios do treino pelo treinoId
    public ArrayList<Exercicio> listarExerciciosPorTreino(int treinoId) {
        ArrayList<Exercicio> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_TREINO_ID + " = ?",
                new String[]{String.valueOf(treinoId)}, null, null, null);

        try {
            while (cursor.moveToNext()) {
                Exercicio e = new Exercicio(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SERIES)),
                        (float) cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PESO)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REPETICOES))
                );
                e.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                lista.add(e);
            }
        } finally {
            cursor.close();
        }

        return lista;
    }
}

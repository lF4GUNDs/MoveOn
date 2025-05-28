package com.example.moveon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExecucaoExercicioDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "execucoes.db";
    private static final int DB_VERSION = 1;

    public ExecucaoExercicioDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE execucoes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "perfil_id INTEGER," +
                "data TEXT," +
                "nome_exercicio TEXT," +
                "serie INTEGER," +
                "peso INTEGER," +
                "reps INTEGER," +
                "descanso INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS execucoes");
        onCreate(db);
    }

    public void salvarExecucao(int perfilId, String data, String nomeExercicio, int serie, int peso, int reps, int descanso) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("perfil_id", perfilId);
        values.put("data", data);
        values.put("nome_exercicio", nomeExercicio);
        values.put("serie", serie);
        values.put("peso", peso);
        values.put("reps", reps);
        values.put("descanso", descanso);
        db.insert("execucoes", null, values);
    }
}

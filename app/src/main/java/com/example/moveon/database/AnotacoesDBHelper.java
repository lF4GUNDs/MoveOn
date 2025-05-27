package com.example.moveon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moveon.models.Anotacao;

import java.util.ArrayList;

public class AnotacoesDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "anotacoes.db";
    private static final int DB_VERSION = 1;

    public AnotacoesDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE anotacoes (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, conteudo TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS anotacoes");
        onCreate(db);
    }

    // Criar nova anotação
    public void salvarAnotacao(String titulo, String conteudo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", titulo);
        values.put("conteudo", conteudo);
        db.insert("anotacoes", null, values);
        db.close();
    }

    // Listar todas as anotações
    public ArrayList<Anotacao> getTodasAnotacoes() {
        ArrayList<Anotacao> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM anotacoes ORDER BY id DESC", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String titulo = cursor.getString(1);
            String conteudo = cursor.getString(2);
            lista.add(new Anotacao(id, titulo, conteudo));
        }
        cursor.close();
        db.close();
        return lista;
    }

    // Buscar anotação por ID
    public Anotacao getAnotacaoPorId(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM anotacoes WHERE id = ?", new String[]{String.valueOf(id)});
        Anotacao anotacao = null;
        if (cursor.moveToFirst()) {
            String titulo = cursor.getString(1);
            String conteudo = cursor.getString(2);
            anotacao = new Anotacao(id, titulo, conteudo);
        }
        cursor.close();
        db.close();
        return anotacao;
    }

    // Atualizar anotação existente
    public void atualizarAnotacao(int id, String titulo, String conteudo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", titulo);
        values.put("conteudo", conteudo);
        db.update("anotacoes", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Excluir anotação
    public void excluirAnotacao(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("anotacoes", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}

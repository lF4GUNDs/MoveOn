package com.example.moveon.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.moveon.models.Perfil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PerfilStorage {
    private static final String PREFS_NAME = "perfil_prefs";
    private static final String KEY_PERFIS = "lista_perfis";

    // Salva um novo perfil
    public static void salvarPerfil(Context context, Perfil novoPerfil) {
        ArrayList<Perfil> perfis = obterPerfis(context);
        perfis.add(novoPerfil);
        salvarLista(context, perfis);
    }

    // Recupera todos os perfis salvos
    public static ArrayList<Perfil> obterPerfis(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_PERFIS, null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<Perfil>>() {}.getType();
            return new Gson().fromJson(json, type);
        }
        return new ArrayList<>();
    }

    // Salva a lista completa (usada para deletar também)
    public static void salvarLista(Context context, ArrayList<Perfil> lista) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(lista);
        editor.putString(KEY_PERFIS, json);
        editor.apply();
    }
}

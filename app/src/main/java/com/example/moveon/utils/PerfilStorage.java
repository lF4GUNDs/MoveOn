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

    // Salva um novo perfil com ID único
    public static void salvarPerfil(Context context, Perfil novoPerfil) {
        ArrayList<Perfil> perfis = obterPerfis(context);

        // Verifica se já existe um perfil com o mesmo ID
        for (Perfil p : perfis) {
            if (p.getId() == novoPerfil.getId()) {
                return; // já existe, não salva duplicado
            }
        }

        perfis.add(novoPerfil);
        salvarLista(context, perfis);
    }

    // Gera um novo ID único baseado na lista atual
    public static int gerarNovoId(Context context) {
        ArrayList<Perfil> perfis = obterPerfis(context);
        int maxId = 0;
        for (Perfil p : perfis) {
            if (p.getId() > maxId) {
                maxId = p.getId();
            }
        }
        return maxId + 1;
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

    // Busca um perfil pelo ID
    public static Perfil buscarPerfilPorId(Context context, int id) {
        ArrayList<Perfil> perfis = obterPerfis(context);
        for (Perfil p : perfis) {
            if (p.getId() == id) return p;
        }
        return null;
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

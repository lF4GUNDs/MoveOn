package com.example.moveon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moveon.R;
import com.example.moveon.models.Anotacao;
import java.util.ArrayList;

public class AnotacaoAdapter extends RecyclerView.Adapter<AnotacaoAdapter.ViewHolder> {

    private ArrayList<Anotacao> lista;
    private Context context;

    public AnotacaoAdapter(ArrayList<Anotacao> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    public void atualizarLista(ArrayList<Anotacao> novaLista) {
        this.lista = novaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_anotacao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Anotacao anotacao = lista.get(position);
        holder.titulo.setText(anotacao.getTitulo());
        holder.conteudo.setText(anotacao.getConteudo());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, conteudo;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.tvTituloAnotacao);
            conteudo = itemView.findViewById(R.id.tvConteudoAnotacao);
        }
    }
}


package com.example.moveon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;
import com.example.moveon.models.HistoricoTreino;

import java.util.List;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.ViewHolder> {

    private final List<HistoricoTreino> lista;

    public HistoricoAdapter(List<HistoricoTreino> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historico, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoricoTreino item = lista.get(position);
        holder.txtData.setText(item.getData());
        holder.txtGrupo.setText(item.getNomeGrupo());
        holder.txtDuracao.setText(item.getDuracaoMinutos() + " min");
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtData, txtGrupo, txtDuracao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtData = itemView.findViewById(R.id.txtData);
            txtGrupo = itemView.findViewById(R.id.txtGrupo);
            txtDuracao = itemView.findViewById(R.id.txtDuracao);
        }
    }
}

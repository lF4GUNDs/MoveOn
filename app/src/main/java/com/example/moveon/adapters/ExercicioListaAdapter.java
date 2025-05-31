
package com.example.moveon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;
import com.example.moveon.models.Exercicio;

import java.util.ArrayList;

public class ExercicioListaAdapter extends RecyclerView.Adapter<ExercicioListaAdapter.ViewHolder> {

    private ArrayList<Exercicio> lista;
    private OnEditarListener editarListener;
    private OnExcluirListener excluirListener;

    public interface OnEditarListener {
        void onEditar(Exercicio exercicio, int posicao);
    }

    public interface OnExcluirListener {
        void onExcluir(int posicao);
    }

    public ExercicioListaAdapter(ArrayList<Exercicio> lista, OnEditarListener editarListener, OnExcluirListener excluirListener) {
        this.lista = lista;
        this.editarListener = editarListener;
        this.excluirListener = excluirListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercicio_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercicio exercicio = lista.get(position);
        holder.tvNome.setText(exercicio.getNome());
        holder.tvSeries.setText(exercicio.getSeriesConcluidas() + "/" + exercicio.getSeries() + " séries concluídas");

        holder.itemView.setOnClickListener(v -> {
            if (exercicio.getSeriesConcluidas() < exercicio.getSeries()) {
                exercicio.setSeriesConcluidas(exercicio.getSeriesConcluidas() + 1);
                notifyItemChanged(position);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            editarListener.onEditar(exercicio, position);
            return true;
        });

        holder.btnExcluir.setOnClickListener(v -> {
            excluirListener.onExcluir(position);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvSeries;
        ImageButton btnExcluir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNomeExercicio);
            tvSeries = itemView.findViewById(R.id.tvSeriesInfo);
            btnExcluir = itemView.findViewById(R.id.btnExcluir);
        }
    }
}

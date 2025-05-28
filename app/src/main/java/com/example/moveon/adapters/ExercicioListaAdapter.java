package com.example.moveon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moveon.R;
import com.example.moveon.models.Exercicio;
import java.util.ArrayList;

public class ExercicioListaAdapter extends RecyclerView.Adapter<ExercicioListaAdapter.ViewHolder> {

    private ArrayList<Exercicio> lista;

    public ExercicioListaAdapter(ArrayList<Exercicio> lista) {
        this.lista = lista;
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
        holder.tvSeries.setText("0/" + exercicio.getSeries() + " séries concluídas");
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvSeries;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNomeExercicio);
            tvSeries = itemView.findViewById(R.id.tvSeriesInfo);
        }
    }
}

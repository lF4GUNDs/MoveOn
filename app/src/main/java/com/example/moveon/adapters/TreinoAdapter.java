package com.example.moveon.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;
import com.example.moveon.activities.ListaDeExerciciosActivity;
import com.example.moveon.models.Treino;

import java.util.ArrayList;

public class TreinoAdapter extends RecyclerView.Adapter<TreinoAdapter.TreinoViewHolder> {

    private ArrayList<Treino> listaTreinos;
    private Context context;
    private int perfilId; // ✅ Novo campo

    public TreinoAdapter(ArrayList<Treino> listaTreinos, Context context, int perfilId) {
        this.listaTreinos = listaTreinos;
        this.context = context;
        this.perfilId = perfilId;
    }

    @NonNull
    @Override
    public TreinoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_treino, parent, false);
        return new TreinoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TreinoViewHolder holder, int position) {
        Treino treino = listaTreinos.get(position);

        holder.nomeTreino.setText(treino.getNome());
        holder.imagemTreino.setImageResource(treino.getImagemResId());

        // Clique no item → enviar treino completo + perfilId via intent
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ListaDeExerciciosActivity.class);
            intent.putExtra("treino", treino);
            intent.putExtra("perfilId", perfilId); // ✅ envia perfilId
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaTreinos.size();
    }

    public static class TreinoViewHolder extends RecyclerView.ViewHolder {
        ImageView imagemTreino;
        TextView nomeTreino;

        public TreinoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemTreino = itemView.findViewById(R.id.imgTreino);
            nomeTreino = itemView.findViewById(R.id.txtNomeTreino);
        }
    }
}

package com.example.moveon.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;
import com.example.moveon.activities.ListaDeExerciciosActivity;
import com.example.moveon.models.Treino;

import java.util.ArrayList;

public class TreinoAdapter extends RecyclerView.Adapter<TreinoAdapter.TreinoViewHolder> {

    private ArrayList<Treino> listaTreinos;
    private Context context;

    public TreinoAdapter(ArrayList<Treino> listaTreinos, Context context) {
        this.listaTreinos = listaTreinos;
        this.context = context;
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

        // Clique no item → enviar treino completo via intent
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ListaDeExerciciosActivity.class);
            intent.putExtra("treino", treino);  // ENVIA O OBJETO TREINO COMPLETO
            context.startActivity(intent);
        });

        // Botão de excluir
        holder.btnExcluir.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Remover treino")
                    .setMessage("Deseja realmente remover este treino?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        int pos = holder.getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            listaTreinos.remove(pos);
                            notifyItemRemoved(pos);
                            notifyItemRangeChanged(pos, listaTreinos.size());
                            Toast.makeText(context, "Treino removido", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return listaTreinos.size();
    }

    public static class TreinoViewHolder extends RecyclerView.ViewHolder {
        ImageView imagemTreino;
        TextView nomeTreino;
        ImageButton btnExcluir;

        public TreinoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemTreino = itemView.findViewById(R.id.imgTreino);
            nomeTreino = itemView.findViewById(R.id.txtNomeTreino);
            btnExcluir = itemView.findViewById(R.id.btnExcluir);
        }
    }
}

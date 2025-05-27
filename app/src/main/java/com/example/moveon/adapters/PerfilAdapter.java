package com.example.moveon.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;
import com.example.moveon.models.Perfil;
import com.example.moveon.utils.PerfilStorage;

import java.util.ArrayList;

public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.PerfilViewHolder> {

    private ArrayList<Perfil> listaPerfis;
    private Context context;
    private OnPerfilClickListener listener;

    public interface OnPerfilClickListener {
        void onPerfilClick(Perfil perfil);
    }

    public PerfilAdapter(ArrayList<Perfil> listaPerfis, Context context, OnPerfilClickListener listener) {
        this.listaPerfis = listaPerfis;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PerfilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_perfil, parent, false);
        return new PerfilViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull PerfilViewHolder holder, int position) {
        Perfil perfil = listaPerfis.get(position);
        holder.nome.setText(perfil.getNome());
        holder.img.setImageResource(perfil.getImagemResId());

        holder.itemView.setOnClickListener(v -> listener.onPerfilClick(perfil));

        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Excluir Perfil")
                    .setMessage("Deseja excluir o perfil \"" + perfil.getNome() + "\"?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        removerPerfil(perfil);
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
            return true;
        });
    }

    private void removerPerfil(Perfil perfil) {
        listaPerfis.remove(perfil);
        notifyDataSetChanged();
        PerfilStorage.salvarLista(context, listaPerfis);
        Toast.makeText(context, "Perfil excluído", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return listaPerfis.size();
    }

    public class PerfilViewHolder extends RecyclerView.ViewHolder {
        TextView nome;
        ImageView img;

        public PerfilViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.txtNomePerfil);
            img = itemView.findViewById(R.id.imgPerfil);
        }
    }
}

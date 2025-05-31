package com.example.moveon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;

public class ImagemAdapter extends RecyclerView.Adapter<ImagemAdapter.ImagemViewHolder> {

    private final Context context;
    private final int[] imagens;
    private final OnImagemClickListener listener;
    private int imagemSelecionadaIndex = -1;

    public interface OnImagemClickListener {
        void onImagemClick(int resId);
    }

    public ImagemAdapter(Context context, int[] imagens, OnImagemClickListener listener) {
        this.context = context;
        this.imagens = imagens;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ImagemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_imagem, parent, false);
        return new ImagemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagemViewHolder holder, int position) {
        int resId = imagens[position];
        holder.imgItem.setImageResource(resId);

        // Aplica destaque se a imagem estiver selecionada
        if (imagemSelecionadaIndex == position) {
            holder.imgItem.setBackground(ContextCompat.getDrawable(context, R.drawable.borda_selecionado));
        } else {
            holder.imgItem.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_background));
        }

        holder.imgItem.setOnClickListener(v -> {
            int posAnterior = imagemSelecionadaIndex;
            imagemSelecionadaIndex = position;
            notifyItemChanged(posAnterior);
            notifyItemChanged(imagemSelecionadaIndex);
            listener.onImagemClick(resId);
        });
    }

    @Override
    public int getItemCount() {
        return imagens.length;
    }

    public static class ImagemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;

        public ImagemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
        }
    }
}

package com.example.moveon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveon.R;

public class ImagemAdapter extends RecyclerView.Adapter<ImagemAdapter.ImagemViewHolder> {

    private final Context context;
    private final int[] imagens;
    private final OnImagemClickListener listener;

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
        holder.imagem.setImageResource(imagens[position]);
        holder.itemView.setOnClickListener(v -> listener.onImagemClick(imagens[position]));
    }

    @Override
    public int getItemCount() {
        return imagens.length;
    }

    static class ImagemViewHolder extends RecyclerView.ViewHolder {
        ImageView imagem;

        public ImagemViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem = itemView.findViewById(R.id.imagemExemplo); // <== Certifique-se que esse ID exista no layout
        }
    }
}

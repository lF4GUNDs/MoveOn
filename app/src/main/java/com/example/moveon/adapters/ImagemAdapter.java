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

    private int[] imagens;
    private Context context;
    private OnImagemClickListener listener;

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
        View view = LayoutInflater.from(context).inflate(R.layout.item_treino, parent, false);
        return new ImagemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagemViewHolder holder, int position) {
        int img = imagens[position];
        holder.imageView.setImageResource(img);
        holder.imageView.setOnClickListener(v -> listener.onImagemClick(img));
    }

    @Override
    public int getItemCount() {
        return imagens.length;
    }

    public static class ImagemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImagemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageButton_Historico);
        }
    }
}

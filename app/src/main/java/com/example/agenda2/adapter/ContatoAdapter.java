package com.example.agenda2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda2.R;
import com.example.agenda2.model.Contato;

import java.util.List;

public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.ViewHolder> {

    public interface OnExcluirClick {
        void onExcluir(int position);
    }

    private final List<Contato> lista;
    private final OnExcluirClick listener;

    public ContatoAdapter(List<Contato> lista, OnExcluirClick listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contato, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        Contato c = lista.get(pos);
        holder.tvNome.setText(c.getNome());
        holder.btnExcluir.setOnClickListener(v -> listener.onExcluir(pos));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome;
        Button btnExcluir;
        ViewHolder(View itemView) {
            super(itemView);
            tvNome     = itemView.findViewById(R.id.tv_nome_item);
            btnExcluir = itemView.findViewById(R.id.btn_excluir_item);
        }
    }
}
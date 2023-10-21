package com.example.miutn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miutn.R;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class AdapterAgregaMateria extends RecyclerView.Adapter<AdapterAgregaMateria.ViewHolder> {
    private ArrayList<String> materias;

    public AdapterAgregaMateria(ArrayList<String> materias) {
        this.materias = materias;
    }

    @NonNull
    @Override
    public AdapterAgregaMateria.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View misMateriasView = inflater.inflate(R.layout.recycler_more_class, parent, false);

        ViewHolder viewHolder =new ViewHolder(misMateriasView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAgregaMateria.ViewHolder holder, int position) {
holder.nombreMateria.setText(materias.get(position));
    }

    @Override
    public int getItemCount() {
        return materias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Chip nombreMateria;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreMateria=itemView.findViewById(R.id.nombreMateria);
        }
    }
}

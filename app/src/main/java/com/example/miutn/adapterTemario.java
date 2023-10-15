package com.example.miutn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miutn.network.models.Temario;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class adapterTemario extends RecyclerView.Adapter<adapterTemario.ViewHolder> {
ArrayList<Temario> temarios;

    public adapterTemario(ArrayList<Temario> temarios) {
        this.temarios = temarios;
    }

    public adapterTemario() {
        temarios=new ArrayList<>();
    }

    @NonNull
    @Override
    public adapterTemario.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View misMateriasView = inflater.inflate(R.layout.recycler_temario, parent, false);
        adapterTemario.ViewHolder viewHolder = new ViewHolder(misMateriasView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull adapterTemario.ViewHolder holder, int position) {
    holder.temeName.setText(temarios.get(position).getTema());
    holder.position=position;
    }

    @Override
    public int getItemCount() {
        return temarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView temeName;
        int position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            temeName=itemView.findViewById(R.id.temeName);
            //-->   Si se realiza un click sobre el nombre tengo que ir al apunte   <--
            // TODO Puedo abrir el link directamente o abrir el archivo en una nueva activity   <--
            temeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(itemView,"Tocaste : "+temeName.getText(),Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }
}

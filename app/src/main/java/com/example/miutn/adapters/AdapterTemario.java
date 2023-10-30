package com.example.miutn.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miutn.R;
import com.example.miutn.network.models.Temario;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/** @noinspection unused*/
public class AdapterTemario extends RecyclerView.Adapter<AdapterTemario.ViewHolder> {
    ArrayList<Temario> temarios;

    public AdapterTemario(ArrayList<Temario> temarios) {
        this.temarios = temarios;
    }

    public AdapterTemario() {
        temarios = new ArrayList<>();
    }

    @NonNull
    @Override
    public AdapterTemario.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View misMateriasView = inflater.inflate(R.layout.recycler_temario, parent, false);
        AdapterTemario.ViewHolder viewHolder = new ViewHolder(misMateriasView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTemario.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.temeName.setText(temarios.get(position).getTema());
        holder.Temario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Tocamos", "POS : " + position);
                Uri link = Uri.parse(temarios.get(position).getApunte()); //-->   Esta es la direccion del apunte <--
                Intent intent = new Intent(Intent.ACTION_VIEW, link);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return temarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView temeName;
        LinearLayout Temario;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            temeName = itemView.findViewById(R.id.temeName);
            Temario = itemView.findViewById(R.id.Temario);
            //-->   Si se realiza un click sobre el nombre tengo que ir al apunte   <--
            // TODO Puedo abrir el link directamente o abrir el archivo en una nueva activity   <--
            temeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(itemView, "Tocaste : " + temeName.getText(), Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }
}
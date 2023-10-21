package com.example.miutn.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miutn.network.models.FechasExamenes;
import com.example.miutn.R;

import java.util.ArrayList;

public class AdapterProximasFechas extends RecyclerView.Adapter<AdapterProximasFechas.ViewHolder> {

    private ArrayList<FechasExamenes> fechasExamenes;

    public AdapterProximasFechas() {
        fechasExamenes=new ArrayList<>();
    }

    public AdapterProximasFechas(ArrayList<FechasExamenes> fechasExamenes) {
        this.fechasExamenes = fechasExamenes;
    }

    @NonNull
    @Override
    public AdapterProximasFechas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View misMateriasView = inflater.inflate(R.layout.recycler_fechas_examenes, parent, false);
        AdapterProximasFechas.ViewHolder viewHolder = new AdapterProximasFechas.ViewHolder(misMateriasView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProximasFechas.ViewHolder holder, int position) {
    holder.proximaFechaExamenFecha.setText(fechasExamenes.get(position).getFecha());
    holder.proximaFechaExamenMateria.setText(fechasExamenes.get(position).getMateria().getNombre());
    holder.cardFechaExamen.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("TOCO","ELEMENTO : "+fechasExamenes.get(position).getFecha());
        }
    });
    }


    @Override
    public int getItemCount() {
        return fechasExamenes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView proximaFechaExamenMateria;
        TextView proximaFechaExamenFecha;
        CardView cardFechaExamen;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            proximaFechaExamenMateria=itemView.findViewById(R.id.proximaFechaExamenMateria);
            proximaFechaExamenFecha=itemView.findViewById(R.id.proximaFechaExamenFecha);
            cardFechaExamen=itemView.findViewById(R.id.cardFechaExamen);
        }
    }
}

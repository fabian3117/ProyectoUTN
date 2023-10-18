package com.example.miutn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

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
    holder.proximaFechaExamenFecha.setText(fechasExamenes.get(position).getFecha().toString());
    holder.proximaFechaExamenMateria.setText(fechasExamenes.get(position).getMateria().getNombre());
    }


    @Override
    public int getItemCount() {
        return fechasExamenes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView proximaFechaExamenMateria;
        TextView proximaFechaExamenFecha;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            proximaFechaExamenMateria=itemView.findViewById(R.id.proximaFechaExamenMateria);
            proximaFechaExamenFecha=itemView.findViewById(R.id.proximaFechaExamenFecha);
        }
    }
}

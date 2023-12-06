package com.example.miutn.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miutn.R;
import com.example.miutn.network.models.NMateria;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

/** @noinspection FieldMayBeFinal*/
public class AdapterAgregaMateria extends RecyclerView.Adapter<AdapterAgregaMateria.ViewHolder> {
    //  TODO Retorno el nombre y luego el servidor hace el macheo correspondiente   <--
//todo Modificar esto para manejar a NMaterias ya fue el consumo de memoria
    private ArrayList<NMateria> materias;
    NMateria activado=new NMateria();

    public NMateria getMateriaSeleccionada() {
        return activado;
    }

    public AdapterAgregaMateria(ArrayList<NMateria> materias) {
        this.materias = materias;
    }

    @NonNull
    @Override
    public AdapterAgregaMateria.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View misMateriasView = inflater.inflate(R.layout.recycler_more_class, parent, false);

        ViewHolder viewHolder = new ViewHolder(misMateriasView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAgregaMateria.ViewHolder holder, int position) {
        holder.nombreMateria.setText(materias.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return materias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Chip nombreMateria;
        public ChipGroup pruebaChipgroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreMateria = itemView.findViewById(R.id.nombreMateria);
            pruebaChipgroup=itemView.findViewById(R.id.pruebaChipgroup);
            Chip tes=new Chip(itemView.getContext());
            tes.setText("Prue-ba");
            pruebaChipgroup.addView(tes);
            nombreMateria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.e("TOCO", "BOTON" + buttonView.getText());
                    //todo modificar este por el codigo que ya tengo en login
//                    activado = String.valueOf(buttonView.getText());
                }
            });
        }
    }
}

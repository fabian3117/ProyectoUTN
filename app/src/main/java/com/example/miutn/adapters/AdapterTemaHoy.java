package com.example.miutn.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miutn.R;
import com.example.miutn.network.models.MateriasCursando;
import com.example.miutn.network.models.Temario;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class AdapterTemaHoy extends RecyclerView.Adapter<AdapterTemaHoy.ViewHolder> {
    private ArrayList<Temario> temarios;

    public AdapterTemaHoy(ArrayList<Temario> temarios) {
        this.temarios = temarios;
    }
    public void setData(ArrayList<Temario> newData){
        temarios=newData;
        notifyDataSetChanged();
    }

    public AdapterTemaHoy() {
    }

    @NonNull
    @Override
    public AdapterTemaHoy.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View temasHoy=inflater.inflate(R.layout.recycler_temas_hoy,parent,false);
        ViewHolder viewHolder=new ViewHolder(temasHoy);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTemaHoy.ViewHolder holder, int position) {
    holder.downloadIssueToday.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Uri link= Uri.parse(temarios.get(position).getApunte()); //-->   Esta es la direccion del apunte <--
            Intent intent=new Intent(Intent.ACTION_VIEW,link);
            v.getContext().startActivity(intent);
        }
    });
    holder.descriptionIssueToday.setText(temarios.get(position).getDescription());
    holder.nameIssueToday.setText(temarios.get(position).getTema());
    holder.seenIssueToday.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Mostrar animacion o algo confirmando
        }
    });
    }

    @Override
    public int getItemCount() {
        return temarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameIssueToday,descriptionIssueToday;
        private Button downloadIssueToday,seenIssueToday;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameIssueToday=itemView.findViewById(R.id.nameIssueToday);
            descriptionIssueToday=itemView.findViewById(R.id.descriptionIssueToday);
            downloadIssueToday=itemView.findViewById(R.id.downloadIssueToday);
            seenIssueToday=itemView.findViewById(R.id.seenIssueToday);
        }
    }
}

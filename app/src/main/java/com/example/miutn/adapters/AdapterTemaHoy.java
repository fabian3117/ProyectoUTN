package com.example.miutn.adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miutn.MainActivity;
import com.example.miutn.R;
import com.example.miutn.network.api.VistaMarkdown;
import com.example.miutn.network.models.Temario;

import java.util.ArrayList;

public class AdapterTemaHoy extends RecyclerView.Adapter<AdapterTemaHoy.ViewHolder> {
    private ArrayList<Temario> temarios;

    public AdapterTemaHoy(ArrayList<Temario> temarios) {
        this.temarios = temarios;
    }

    public void setData(ArrayList<Temario> newData) {
        temarios = newData;
        notifyDataSetChanged();
    }

    public AdapterTemaHoy() {
        temarios = new ArrayList<>();
    }

    @NonNull
    @Override
    public AdapterTemaHoy.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View temasHoy = inflater.inflate(R.layout.recycler_temas_hoy, parent, false);
        ViewHolder viewHolder = new ViewHolder(temasHoy);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTemaHoy.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.downloadIssueToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String documentID=temarios.get(position).getId();

                Intent intent=new Intent(v.getContext(), VistaMarkdown.class);
                Bundle bundle=new Bundle();
                bundle.getString("nameFile",documentID);
                intent.putExtra("documentID",documentID);

                v.getContext().startActivity(intent);
                //v.getContext().finish();
                /*Uri link = Uri.parse(temarios.get(position).getApunte()); //-->   Esta es la direccion del apunte <--
                Intent intent = new Intent(Intent.ACTION_VIEW, link);
                v.getContext().startActivity(intent);


                 */
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

    /** @noinspection FieldMayBeFinal, FieldMayBeFinal, FieldMayBeFinal, FieldMayBeFinal */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameIssueToday, descriptionIssueToday;
        private Button downloadIssueToday, seenIssueToday;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameIssueToday = itemView.findViewById(R.id.nameIssueToday);
            descriptionIssueToday = itemView.findViewById(R.id.descriptionIssueToday);
            downloadIssueToday = itemView.findViewById(R.id.downloadIssueToday);
            seenIssueToday = itemView.findViewById(R.id.seenIssueToday);
        }
    }
}

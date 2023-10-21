package com.example.miutn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miutn.R;
import com.example.miutn.network.models.MateriasCursando;
import com.example.miutn.network.models.Temario;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class AdapterMisMaterias extends RecyclerView.Adapter<AdapterMisMaterias.ViewHolder> {
    private ArrayList<MateriasCursando> Materias;
    //private Context context;


    public AdapterMisMaterias(ArrayList<MateriasCursando> materias) {
        Materias = materias;
    }


    public AdapterMisMaterias() {
        Materias = new ArrayList<MateriasCursando>();
    }
    public void setData(ArrayList<MateriasCursando> newData){
        Materias=newData;
           notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterMisMaterias.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View misMateriasView = inflater.inflate(R.layout.recycleview_materias_cursando, parent, false);
        ViewHolder viewHolder = new ViewHolder(misMateriasView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMisMaterias.ViewHolder holder, int position) {
    //-->   This i'm make link elemento to position of my Array <--
        holder.materiaName.setText(Materias.get(position).getMateria().getNombre()); //-->   Link my content with my element
        holder.position=position;
        holder.materiaSede.setText(Materias.get(position).getSede());
        holder.chipAnio.setText(String.valueOf(Materias.get(position).getMateria().getAnio()));
    }

    @Override
    public int getItemCount() {
        return Materias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
private TextView materiaName;
private TextView materiaSede;
private LinearLayout linearLayout;
private ImageView imageView;
private Chip chipAnio;
private int position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            materiaName=itemView.findViewById(R.id.materiaName);
            linearLayout=itemView.findViewById(R.id.parentMatery);
            materiaSede=itemView.findViewById(R.id.materiaSede);
            imageView=itemView.findViewById(R.id.imageView);
            chipAnio=itemView.findViewById(R.id.chipAnio);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    BottomSheetDialog sideSheetDialog=new BottomSheetDialog(v.getContext());
                   // sideSheetDialog.setContentView(R.layout.tryshide);
                    LayoutInflater inflater=(LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view=inflater.inflate(R.layout.tryshide,null);
                    Chip textView=view.findViewById(R.id.nameClass);

                    textView.setText(materiaName.getText());
                    TextView textView1=view.findViewById(R.id.classCampus);
                    textView1.setText(Materias.get(position).getDia());
                    Chip sede=view.findViewById(R.id.sedechip);
                    sede.setText(Materias.get(position).getSede());
                    Chip classHours=view.findViewById(R.id.classHours);
                    classHours.setText(Materias.get(position).getHorario());
                    RecyclerView recyclerViewTemario=view.findViewById(R.id.RecyclerTemario);
                    ArrayList<Temario>temarios=new ArrayList<>();
                    for(int a=0;a<5;a++){
                        Temario temario=new Temario();
                        temario.setApunte("https://www.google.com.ar");
                        temario.setTema("Tema" +a);
                        temarios.add(temario);
                    }
                    Materias.get(position).getMateria().setProgramaAnalitico(temarios);
                    adapterTemario adapterTemario=new adapterTemario(Materias.get(position).getMateria().getProgramaAnalitico());
                    recyclerViewTemario.setAdapter(adapterTemario);

                    recyclerViewTemario.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
                    sideSheetDialog.setContentView(view);
                    // View view = View.inflate(R.layout.tryshide, null,null);
                    sideSheetDialog.show();
                }
            });
        }
    }
}

package com.example.miutn.adapters;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miutn.R;
import com.example.miutn.network.models.NMateriasCursando;
import com.example.miutn.network.models.NprogramaAnalitico;
import com.example.miutn.network.models.Temario;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

/** @noinspection unused*/
public class AdapterMisMaterias extends RecyclerView.Adapter<AdapterMisMaterias.ViewHolder> {
    private ArrayList<NMateriasCursando> Materias;
    //private Context context;


    public AdapterMisMaterias(ArrayList<NMateriasCursando> materias) {
        Materias = materias;
    }


    public AdapterMisMaterias() {
        Materias = new ArrayList<NMateriasCursando>();
    }

    public void setData(ArrayList<NMateriasCursando> newData) {
        Materias = newData;
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
    public void onBindViewHolder(@NonNull AdapterMisMaterias.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //-->   This i'm make link elemento to position of my Array <--
        holder.materiaName.setText(Materias.get(position).getMateria().getName()); //-->   Link my content with my element
        holder.position = position;
        holder.materiaSede.setText(Materias.get(position).getHorario().getSede().getValorAsociado());
        holder.chipAnio.setText(String.valueOf(Materias.get(position).getMateria().getAnio()));

    }

    @Override
    public int getItemCount() {
        return Materias.size();
    }

    /** @noinspection FieldMayBeFinal, FieldMayBeFinal, FieldMayBeFinal, FieldMayBeFinal, FieldMayBeFinal, FieldMayBeFinal, FieldMayBeFinal */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView materiaName;
        private TextView materiaSede;
        private CardView cardView;
        private LinearLayout linearLayout;
        private Chip chipAnio;
        private View viewTest;
        private float porcentaje = 50f;
        private int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            materiaName = itemView.findViewById(R.id.materiaName);
            linearLayout = itemView.findViewById(R.id.parentMatery);
            materiaSede = itemView.findViewById(R.id.materiaSede);
            cardView = itemView.findViewById(R.id.cardView);
            viewTest = itemView.findViewById(R.id.viewTest);
            if (porcentaje < 45) {
                materiaName.setTextColor(Color.parseColor("#000000"));
            }
            ViewGroup.LayoutParams params = viewTest.getLayoutParams();
            ViewGroup.LayoutParams paramsCard = cardView.getLayoutParams();
            params.width = (int) (paramsCard.width * (porcentaje / 100));
            int nuevoAncho = (int) (paramsCard.width * (porcentaje / 100));
            ValueAnimator animator = ValueAnimator.ofInt(0, nuevoAncho);
            animator.setInterpolator(new LinearInterpolator());
            animator.setDuration(1000); // Establece la duración de la animación en milisegundos
            animator.addUpdateListener(animation -> {
                int animatedValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams params1 = viewTest.getLayoutParams();
                params1.width = animatedValue;
                viewTest.setLayoutParams(params1);
            });
            animator.start();
            chipAnio = itemView.findViewById(R.id.chipAnio);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    BottomSheetDialog sideSheetDialog = new BottomSheetDialog(v.getContext());
                    LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.tryshide, null);
                    Chip textView = view.findViewById(R.id.nameClass);
                    textView.setText(materiaName.getText());
                    TextView textView1 = view.findViewById(R.id.classCampus);
                    textView1.setText(Materias.get(position).getHorario().getDia());
                    Chip sede = view.findViewById(R.id.sedechip);
                    sede.setText(Materias.get(position).getHorario().getSede().getValorAsociado());
                    Chip classHours = view.findViewById(R.id.classHours);
                    classHours.setText(Materias.get(position).getHorario().getHoraFin());
                    RecyclerView recyclerViewTemario = view.findViewById(R.id.RecyclerTemario);
                    ArrayList<Temario> temarios = new ArrayList<>();
                    for (int a = 0; a < 5; a++) {
                        Temario temario = new Temario();
                        temario.setApunte("https://www.google.com.ar");
                        temario.setTema("Tema" + a);
                        temarios.add(temario);
                    }
                    NprogramaAnalitico analitico = new NprogramaAnalitico();
                    analitico.setTemas(temarios);
                    Materias.get(position).getMateria().setProgramaAnalitico(analitico);
                    AdapterTemario adapterTemario = new AdapterTemario(Materias.get(position).getMateria().getProgramaAnalitico().getTemas());
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

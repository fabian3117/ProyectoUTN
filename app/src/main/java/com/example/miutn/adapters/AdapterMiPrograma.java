package com.example.miutn.adapters;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miutn.R;
import com.example.miutn.network.models.NMateria;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

/**
 * Muestra todas las materias de la carrera
 * @noinspection unused
 */
public class AdapterMiPrograma extends RecyclerView.Adapter<AdapterMiPrograma.ViewHolder> {
    ArrayList<NMateria> materiasDeCarrera;

    public AdapterMiPrograma(ArrayList<NMateria> materiasDeCarrera) {
        this.materiasDeCarrera = materiasDeCarrera;
    }

    public AdapterMiPrograma() {
        this.materiasDeCarrera = new ArrayList<>();
    }

    @NonNull
    @Override
    public AdapterMiPrograma.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View misMateriasView = inflater.inflate(R.layout.recycleview_materias_carrera, parent, false);
        AdapterMiPrograma.ViewHolder viewHolder = new ViewHolder(misMateriasView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMiPrograma.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.linearLayout.setOnClickListener(v -> {
            BottomSheetDialog sideSheetDialog = new BottomSheetDialog(v.getContext());
            LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.tryshide, null);
            Chip textView = view.findViewById(R.id.nameClass);
           ;
            textView.setText(materiasDeCarrera.get(position).getName());
            RecyclerView recyclerViewTemario = view.findViewById(R.id.RecyclerTemario);
            AdapterTemario adapterTemario = new AdapterTemario(materiasDeCarrera.get(position).getProgramaAnalitico().getTemas());
            recyclerViewTemario.setAdapter(adapterTemario);
            recyclerViewTemario.setLayoutManager(new LinearLayoutManager(v.getContext()));
            sideSheetDialog.setContentView(view);
            sideSheetDialog.show();
        });
        holder.materiaName.setText(materiasDeCarrera.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return materiasDeCarrera.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<NMateria> cursando) {
        materiasDeCarrera = cursando;
        notifyDataSetChanged();
    }

    /** @noinspection FieldMayBeFinal, FieldMayBeFinal, FieldMayBeFinal, FieldMayBeFinal, FieldMayBeFinal */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView materiaName;
        private TextView materiaSede;
        private CardView cardView;
        private LinearLayout linearLayout;
        private ImageView imageView;
        private Chip chipAnio;
        private View viewTest;
        private float porcentaje = 30f;
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
            viewTest.getLayoutParams().width = (int) (paramsCard.width * (porcentaje / 100));
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
        }
    }
}

package com.example.miutn.fragment;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.miutn.R;
import com.example.miutn.network.models.Perfil;
import com.google.android.material.chip.Chip;
import com.google.android.material.textview.MaterialTextView;

/** @noinspection FieldMayBeFinal*/
public class perfilFragment extends Fragment {
    Perfil profile = new Perfil();
    private ImageView profilePic;

    private MaterialTextView profileName;
    private TextView numberLegajo;
    private TextView carrera;
    private Chip userSIU, chipProfileCorreo;
    private Chip chipProfileMateriasAprobadas, chipProfileFinalesRestantes, chipProfilePodesCursar;

    private CardView CardTest;
    private int porcentaje = 50;

    public perfilFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);
        profilePic = v.findViewById(R.id.profilePic);
        profileName = v.findViewById(R.id.profileName);
        numberLegajo = v.findViewById(R.id.numberLegajo);
        userSIU = v.findViewById(R.id.userSIU);
        carrera = v.findViewById(R.id.carrera);
        chipProfilePodesCursar = v.findViewById(R.id.chipProfilePodesCursar);
        chipProfileFinalesRestantes = v.findViewById(R.id.chipProfileFinalesRestantes);
        chipProfileMateriasAprobadas = v.findViewById(R.id.chipProfileMateriasAprobadas);
        chipProfileCorreo = v.findViewById(R.id.chipProfileCorreo);
        CardTest = v.findViewById(R.id.CardTest);
        View ViewProfileMateriasAprobadas = v.findViewById(R.id.ViewProfileMateriasAprobadas);
        @SuppressLint("CutPasteId") ViewGroup.LayoutParams paramsPater = v.findViewById(R.id.CardTest).getLayoutParams();
        AjustePorcentajes(paramsPater.width, ViewProfileMateriasAprobadas);
        CargaTestData();
        return v;
    }

    void AjustePorcentajes(int anchoMax, View ViewProfileMateriasAprobadas) {
        ViewGroup.LayoutParams params = ViewProfileMateriasAprobadas.getLayoutParams();
        ViewProfileMateriasAprobadas.setLayoutParams(params);
        ValueAnimator animator = ValueAnimator.ofInt(0, anchoMax);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                params.width = animatedValue;
                ViewProfileMateriasAprobadas.setLayoutParams(params);
            }
        });
        animator.start();
    }

    void CargaTestData() {
        numberLegajo.setText(profile.getLegajo());
        userSIU.setText(profile.getUserSIU());
        profileName.setText(profile.getUserName());
        carrera.setText(profile.getCarrea().getValorAsociado());
        chipProfileFinalesRestantes.setText(String.valueOf(profile.getMateriasCursadas().size() - profile.getMateriasCursando().size()));
        chipProfileMateriasAprobadas.setText(String.valueOf(profile.getMateriasCursadas().size()));
        chipProfileCorreo.setText(String.valueOf(profile.getCorreoInstitucional()));
        chipProfilePodesCursar.setText("5");


    }


    public void ActualizacionDatosContenidosAdapterProfile(Perfil profile) {
        this.profile = profile;
        // CargaTestData();
    }
}
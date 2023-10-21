package com.example.miutn.fragment;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miutn.R;
import com.example.miutn.enums.Carreras;
import com.google.android.material.chip.Chip;
import com.google.android.material.textview.MaterialTextView;

public class perfilFragment extends Fragment {
private ImageView profilePic;

private MaterialTextView profileName;
private TextView numberLegajo;
private TextView carrera;
private Chip userSIU,chipProfileCorreo;
private Chip chipProfileMateriasAprobadas,chipProfileFinalesRestantes,chipProfilePodesCursar;

    private CardView CardTest;
    private int porcentaje = 50;

    public perfilFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_perfil, container, false);
        profilePic=v.findViewById(R.id.profilePic);
        profileName=v.findViewById(R.id.profileName);
        numberLegajo=v.findViewById(R.id.numberLegajo);
        userSIU=v.findViewById(R.id.userSIU);
        carrera=v.findViewById(R.id.carrera);
        chipProfilePodesCursar=v.findViewById(R.id.chipProfilePodesCursar);
        chipProfileFinalesRestantes=v.findViewById(R.id.chipProfileFinalesRestantes);
        chipProfileMateriasAprobadas=v.findViewById(R.id.chipProfileMateriasAprobadas);
        chipProfileCorreo=v.findViewById(R.id.chipProfileCorreo);
        CardTest=v.findViewById(R.id.CardTest);
        CargaTestData();
      //  ajustarPorcentaje(.10);
        return v;
    }
    void CargaTestData(){
        numberLegajo.setText("1648408");
        userSIU.setText("fabian3117");
        profileName.setText("Federico Gonzalez");
        carrera.setText(Carreras.Electronica.toString());
        chipProfileFinalesRestantes.setText("22");
        chipProfilePodesCursar.setText("5");
        chipProfileMateriasAprobadas.setText("10");
        chipProfileCorreo.setText("fabian3117@frba.utn.edu.ar");
    }
    void ajustarPorcentaje(double porc){
        GradientDrawable fondoPorcentaje = new GradientDrawable();
        fondoPorcentaje.setColor(getResources().getColor(R.color.colorPorcentaje)); // Cambia el color según tus necesidades
        fondoPorcentaje.setCornerRadii(new float[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        fondoPorcentaje.setSize((int) (CardTest.getWidth() * (porc / 100.0)), CardTest.getHeight());

        // Establecer el fondo del CardView
        CardTest.setBackground(fondoPorcentaje);

        int totalAncho = CardTest.getWidth();
        int anchoPorcentaje = (totalAncho * porcentaje) / 100;

    }
}
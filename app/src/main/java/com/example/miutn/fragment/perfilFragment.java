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
import com.example.miutn.network.models.Profile;
import com.google.android.material.chip.Chip;
import com.google.android.material.textview.MaterialTextView;

public class perfilFragment extends Fragment {
    Profile profile=new Profile();
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
        return v;
    }
    void CargaTestData(){
        numberLegajo.setText(profile.getNumberLegajo());
        userSIU.setText(profile.getUserSIU());
        profileName.setText(profile.getName());
        carrera.setText(profile.getCarrera());
        chipProfileFinalesRestantes.setText(String.valueOf(profile.getMateriasCursadasRegularizadas().size()-profile.getMateriasCursadasAprobadas().size()));
        chipProfileMateriasAprobadas.setText(String.valueOf(profile.getMateriasCursadasAprobadas().size()));
        chipProfileCorreo.setText(String.valueOf(profile.getName()));
        chipProfilePodesCursar.setText("5");


    }


    public void ActualizacionDatosContenidosAdapterProfile(Profile profile) {
        this.profile=profile;
       // CargaTestData();
    }
}
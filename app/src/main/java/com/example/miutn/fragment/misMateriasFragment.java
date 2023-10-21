package com.example.miutn.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miutn.R;
import com.example.miutn.adapters.AdapterMisMaterias;
import com.example.miutn.network.models.Materia;
import com.example.miutn.network.models.MateriasCursando;
import com.example.miutn.network.models.Temario;

import java.util.ArrayList;

public class misMateriasFragment extends Fragment {

    RecyclerView totalMaterias;
    AdapterMisMaterias adapterMisMaterias;


    public misMateriasFragment() {
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
        View view=inflater.inflate(R.layout.fragment_mis_materias, container, false);
        totalMaterias=view.findViewById(R.id.totalMaterias);
        ArrayList<MateriasCursando> materiasTest=new ArrayList<>();
        MateriasCursando materiasCursando=new MateriasCursando();
        Materia materia=new Materia();

        materia.setNombre("FISICA");
        materia.setAnio(1);
        materia.setCuatri(1);
        materiasCursando.setSede("Campus");
        materiasCursando.setAula("S05");
        Temario temario=new Temario();
        temario.setApunte("https://www.google.com.ar");
        temario.setTema("APUNTE 1");
        ArrayList<Temario> temarios=new ArrayList<Temario>();
        temarios.add(temario);
        materia.setProgramaAnalitico(temarios);
        materiasCursando.setMateria(materia);
        materiasTest.add(materiasCursando);
        adapterMisMaterias=new AdapterMisMaterias(materiasTest);
        totalMaterias.setAdapter(adapterMisMaterias);
        totalMaterias.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //adapterMisMaterias.setData(materiasTest);
        return  view;
    }
}
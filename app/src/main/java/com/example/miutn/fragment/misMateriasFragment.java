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
import com.example.miutn.enums.Carreras;
import com.example.miutn.enums.Cuatrimestres;
import com.example.miutn.enums.Sedes;
import com.example.miutn.network.models.Horarios;
import com.example.miutn.network.models.Materia;
import com.example.miutn.network.models.MateriasCursando;
import com.example.miutn.network.models.NMateria;
import com.example.miutn.network.models.NMateriasCursando;
import com.example.miutn.network.models.NprogramaAnalitico;
import com.example.miutn.network.models.Temario;

import java.util.ArrayList;

public class misMateriasFragment extends Fragment {

    RecyclerView totalMaterias;
    AdapterMisMaterias adapterMisMaterias=new AdapterMisMaterias();
    ArrayList<NMateriasCursando> materiasCursando=new ArrayList<>();
    ArrayList<NMateria> programa=new ArrayList<>();

    public misMateriasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void ActualizacionDatosContenidosAdapterMisMaterias(ArrayList<NMateria> carrera,ArrayList<NMateriasCursando> cursando){
        //-->   todo   Recibo todas las materias de la carrera y las masterias que estoy cursando  <--
        //-->   En caso de
        materiasCursando=cursando;
        programa=carrera;
        adapterMisMaterias.setData(cursando);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mis_materias, container, false);
        totalMaterias=view.findViewById(R.id.totalMaterias);
        totalMaterias.setAdapter(adapterMisMaterias);
        totalMaterias.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //adapterMisMaterias.setData(materiasTest);
        return  view;
    }

    public void ActualizacionDatosContenidosAdapterMisMaterias_Programa(ArrayList<NMateria> body) {
        programa=body;
    }
}
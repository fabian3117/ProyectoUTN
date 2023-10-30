package com.example.miutn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miutn.R;
import com.example.miutn.adapters.AdapterMiPrograma;
import com.example.miutn.network.models.NMateria;
import com.example.miutn.network.models.NMateriasCursando;

import java.util.ArrayList;

/** @noinspection unused, unused */
public class misMateriasFragment extends Fragment {

    RecyclerView totalMaterias;
    AdapterMiPrograma adapterMisMaterias = new AdapterMiPrograma();
    ArrayList<NMateriasCursando> materiasCursando = new ArrayList<>();
    ArrayList<NMateria> programa = new ArrayList<>();

    public misMateriasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void ActualizacionDatosContenidosAdapterMisMaterias(ArrayList<NMateria> carrera, ArrayList<NMateriasCursando> cursando) {
        //-->   todo   Recibo todas las materias de la carrera y las masterias que estoy cursando  <--
        //-->   En caso de
        materiasCursando = cursando;
        programa = carrera;
        adapterMisMaterias.setData(carrera);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mis_materias, container, false);
        totalMaterias = view.findViewById(R.id.totalMaterias);
        totalMaterias.setAdapter(adapterMisMaterias);
        totalMaterias.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //adapterMisMaterias.setData(materiasTest);
        return view;
    }

    public void ActualizacionDatosContenidosAdapterMisMaterias_Programa(ArrayList<NMateria> body) {
        programa = body;
    }
}
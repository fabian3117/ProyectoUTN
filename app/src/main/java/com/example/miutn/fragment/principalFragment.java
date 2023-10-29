package com.example.miutn.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miutn.ControlDatos;
import com.example.miutn.network.models.FechasExamenes;
import com.example.miutn.R;
import com.example.miutn.adapters.AdapterMisMaterias;
import com.example.miutn.adapters.AdapterProximasFechas;
import com.example.miutn.adapters.AdapterTemaHoy;
import com.example.miutn.enums.Cuatrimestres;
import com.example.miutn.network.api.ApiService;
import com.example.miutn.network.api.RetrofitClient;
import com.example.miutn.network.models.Materia;
import com.example.miutn.network.models.MateriasCursando;
import com.example.miutn.network.models.NMateriasCursando;
import com.example.miutn.network.models.Temario;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class principalFragment extends Fragment {

    private RecyclerView RecyclerMismaterias,RecyclerMismateriasHoy,RecyclerProximasFechas,RecyclerTemasHoy;
    AdapterMisMaterias adapterMisMaterias=new AdapterMisMaterias();
    ArrayList<Temario> temarios=new ArrayList<>();
    AdapterMisMaterias adapterMisMateriasHoy=new AdapterMisMaterias();
    //AdapterTemaHoy adapterTemaHoy=new AdapterTemaHoy();
    AdapterTemaHoy adapterTemaHoy=new AdapterTemaHoy();
    ArrayList<NMateriasCursando> materias= new ArrayList<>();
    ArrayList<NMateriasCursando> materiashoy=new ArrayList<>();
    //AdapterProximasFechas adapterProximasFechas=new AdapterProximasFechas();
    AdapterProximasFechas adapterProximasFechas=new AdapterProximasFechas();
    Retrofit retrofit = RetrofitClient.getClient();

    ApiService apiService = retrofit.create(ApiService.class);
    public principalFragment() {
        // Required empty public constructor
    }
public void ActualizacionDatosContenidosAdapterTemario(ArrayList<Temario> apuntesHoyRecomendaciones){
        //-->   Vamos a actualizar los adapters <--
    adapterTemaHoy.setData(apuntesHoyRecomendaciones);

}

public void ActualizacionDatosContenidosAdapterMaterias(ArrayList<NMateriasCursando> mt){
    materias=mt;
    adapterMisMaterias.setData(materias);
}
public void ActualizacionDatosContenidosAdapterFechasEx(ArrayList<FechasExamenes>fechasExamenes){
        adapterProximasFechas.setData(fechasExamenes);
}
public void ActualizacionDatosContenidosAdapterMateriasHoy(ArrayList<NMateriasCursando> mathoy){
     materiashoy=mathoy;
     adapterMisMateriasHoy.setData(mathoy);
}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO MODIFICAR
        String profileTest="FEDE";/*
        Call<ArrayList<MateriasCursando>> call = apiService.obtenerMaterias(profileTest);
        call.enqueue(new Callback<ArrayList<MateriasCursando>>() {
            @Override
            public void onResponse(Call<ArrayList<MateriasCursando>> call, Response<ArrayList<MateriasCursando>> response) {
                if (response.isSuccessful()) {
                    //-->   Si tenemos exito mostramos lo que nos retorno el servidor   <--

                    //ArrayList<MateriasCursando> materias = response.body();
                    materias=response.body();
                    ArrayList<String> nameMatery=new ArrayList<>();
                    for(MateriasCursando mater : materias){
                        nameMatery.add(mater.getMateria().getNombre());
                    }
                    adapterMisMaterias.setData(materias);

                } else {
                    // Maneja el error de la solicitud, por ejemplo, mostrando un mensaje de error.
                    Log.e("MIRA","FALLO");
                }
            }


            @Override
            public void onFailure(Call<ArrayList<MateriasCursando>> call, Throwable t) {
                // Maneja errores de red o excepciones, por ejemplo, mostrando un mensaje de error.
                Log.e("MIRA","FALLO SEGUNDA FORMA");
                Log.e("MIRA",t.getMessage());
                //-->   Obtengo de sharedPreference la informacion  <--

                MateriasCursando materiasCursando=new MateriasCursando();
                Materia materia=new Materia();
                materia.setCuatri(Cuatrimestres.PrimerCuatrimestre.getValorAsociado());
                materia.setAnio(1);
                materia.setNombre("Informatica 1");
                materiasCursando.setMateria(materia);
                materiasCursando.setDia("Jueves");
                materiasCursando.setAula("S51");
                materiasCursando.setHorario("T2");
                materiasCursando.setSede("Campus");
                Temario temario=new Temario();
                temario.setTema("Introduccion Informatica 1");
                temario.setApunte("https://www.google.com.ar");
                ArrayList<Temario> programaAnal=new ArrayList<>();
                programaAnal.add(temario);
                materia.setProgramaAnalitico(programaAnal);
            }
        });*/
        Date date=new Date();
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("EEEE", Locale.getDefault());
        String Dia= simpleDateFormat.format(date);
        Log.e("DIAA",new SimpleDateFormat("EEEE",Locale.getDefault()).format(new Date()));
        Log.e("DIA",Dia);
        Dia="Lunes";    //TODO ELIMINAR ESTO
        Call<ArrayList<MateriasCursando>> MateriasHoy = apiService.obtenerMateriasHoy(profileTest,Dia);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_principal, container, false);
        linkElement(view);
        return view;
    }
    public void linkElement(View v) {
        RecyclerMismaterias = v.findViewById(R.id.RecyclerMismaterias);
        RecyclerMismaterias.setAdapter(adapterMisMaterias);
        RecyclerMismaterias.setLayoutManager(new LinearLayoutManager(v.getContext()));
        RecyclerMismateriasHoy = v.findViewById(R.id.RecyclerMismateriasHoy);
        RecyclerMismateriasHoy.setAdapter(adapterMisMateriasHoy);
        RecyclerMismateriasHoy.setLayoutManager(new LinearLayoutManager(v.getContext()));
        RecyclerProximasFechas=v.findViewById(R.id.RecyclerProximasFechas);
        RecyclerTemasHoy=v.findViewById(R.id.RecyclerTemasHoy);
        //-->    TODO Aca esta la obtencion de apuntes para hoy/recomendados <--
        temarios=ControlDatos.ObtenerRecomendaciones(v.getContext());
        RecyclerTemasHoy.setAdapter(adapterTemaHoy);
        LinearLayoutManager linearLayout=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerTemasHoy.setLayoutManager(linearLayout);

       // adapterProximasFechas=new AdapterProximasFechas(fechasExamenes1);
        RecyclerProximasFechas.setAdapter(adapterProximasFechas);
        RecyclerProximasFechas.setLayoutManager(new LinearLayoutManager(v.getContext()));

       // sideSheetContainer = v.findViewById(R.id.sideSheetContainer);
        //extendedFloatingActionButton = v.findViewById(R.id.fab);
    }
}
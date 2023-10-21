package com.example.miutn.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.miutn.network.models.Temario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link principalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class principalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView RecyclerMismaterias,RecyclerMismateriasHoy,RecyclerProximasFechas,RecyclerTemasHoy;
    AdapterMisMaterias adapterMisMaterias=new AdapterMisMaterias();
    AdapterMisMaterias adapterMisMateriasHoy=new AdapterMisMaterias();
    //AdapterTemaHoy adapterTemaHoy=new AdapterTemaHoy();
    AdapterTemaHoy adapterTemaHoy;
    ArrayList<MateriasCursando> materias= new ArrayList<>();
    ArrayList<MateriasCursando> materiashoy=new ArrayList<>();
    //AdapterProximasFechas adapterProximasFechas=new AdapterProximasFechas();
    AdapterProximasFechas adapterProximasFechas;
    Retrofit retrofit = RetrofitClient.getClient();

    ApiService apiService = retrofit.create(ApiService.class);

    public principalFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO MODIFICAR
        String profileTest="FEDE";
        Call<ArrayList<MateriasCursando>> call = apiService.obtenerMaterias(profileTest);
        call.enqueue(new Callback<ArrayList<MateriasCursando>>() {
            @Override
            public void onResponse(Call<ArrayList<MateriasCursando>> call, Response<ArrayList<MateriasCursando>> response) {
                if (response.isSuccessful()) {
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
                materias.add(materiasCursando);
                adapterMisMaterias.setData(materias);
                // Log.e("MIRA",t.getCause().toString());
            }
        });
        Date date=new Date();
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("EEEE", Locale.getDefault());
        String Dia= simpleDateFormat.format(date);
        Log.e("DIAA",new SimpleDateFormat("EEEE",Locale.getDefault()).format(new Date()));
        Log.e("DIA",Dia);
        Dia="Lunes";    //TODO ELIMINAR ESTO
        Call<ArrayList<MateriasCursando>> MateriasHoy = apiService.obtenerMateriasHoy(profileTest,Dia);
        MateriasHoy.enqueue(new Callback<ArrayList<MateriasCursando>>() {
            @Override
            public void onResponse(Call<ArrayList<MateriasCursando>> call, Response<ArrayList<MateriasCursando>> response) {
                if (response.isSuccessful()) {
                    //ArrayList<MateriasCursando> materiashoy = response.body();
                    materiashoy = response.body();
                    ArrayList<String> nameMateryhoy=new ArrayList<>();
                    if(materiashoy.isEmpty()){
                        // TODO : Añadir Animacion indicando dia libre
                        Log.e("MIRA","ESTA VACIO HOY");
                        return;
                    }
                    for(MateriasCursando mater : materiashoy){
                        nameMateryhoy.add(mater.getMateria().getNombre());
                    }
                    adapterMisMateriasHoy.setData(materiashoy);
                } else {
                    // Maneja el error de la solicitud, por ejemplo, mostrando un mensaje de error.
                    Log.e("MIRA","FALLO");
                }

                //CargaFragment();
            }

            @Override
            public void onFailure(Call<ArrayList<MateriasCursando>> call, Throwable t) {
                // Maneja errores de red o excepciones, por ejemplo, mostrando un mensaje de error.
                Log.e("MIRA","FALLO SEGUNDA FORMA");
                Log.e("MIRA",t.getMessage());
                MateriasCursando materiasCursando=new MateriasCursando();
                materiasCursando.setHorario("T1");
                materiasCursando.setSede("Campus");
                materiasCursando.setAula("S55");
                Materia materia=new Materia();
                materia.setNombre("Fisica 1");
                materia.setCuatri(Cuatrimestres.PrimerCuatrimestre.getValorAsociado());

                materiasCursando.setDia("Lunes");
                materiasCursando.setMateria(materia);
                materiashoy.add(materiasCursando);
                adapterMisMateriasHoy.setData(materiashoy);


                // Log.e("MIRA",t.getCause().toString());
            }
        });
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
       ArrayList<Temario> temarios=new ArrayList<>();
        for(int i=0;i<5;i++){
            Temario temario= new Temario();
            temario.setApunte("https://www.google.com.ar");
            temario.setTema("Issue : "+i);
            temarios.add(temario);
        }
        adapterTemaHoy=new AdapterTemaHoy(temarios);
        RecyclerTemasHoy.setAdapter(adapterTemaHoy);
        LinearLayoutManager linearLayout=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerTemasHoy.setLayoutManager(linearLayout);
        FechasExamenes fechasExamenes=new FechasExamenes();
        String testFecha="18/10/2023";
        fechasExamenes.setFecha(testFecha);
        Materia materia=new Materia();
        materia.setNombre("TEST FINAL");
        fechasExamenes.setMateria(materia);
        ArrayList<FechasExamenes>fechasExamenes1=new ArrayList<>();
        fechasExamenes1.add(fechasExamenes);
        fechasExamenes1.add(fechasExamenes);
        adapterProximasFechas=new AdapterProximasFechas(fechasExamenes1);
        RecyclerProximasFechas.setAdapter(adapterProximasFechas);
        RecyclerProximasFechas.setLayoutManager(new LinearLayoutManager(v.getContext()));

       // sideSheetContainer = v.findViewById(R.id.sideSheetContainer);
        //extendedFloatingActionButton = v.findViewById(R.id.fab);
    }
}
package com.example.miutn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miutn.network.api.ApiService;
import com.example.miutn.network.api.RetrofitClient;
import com.example.miutn.network.models.MateriasCursando;

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
    private RecyclerView RecyclerMismaterias;
    private RecyclerView RecyclerMismateriasHoy;
    AdapterMisMaterias adapterMisMaterias=new AdapterMisMaterias();
    AdapterMisMaterias adapterMisMateriasHoy=new AdapterMisMaterias();
    ArrayList<MateriasCursando> materias= new ArrayList<>();
    ArrayList<MateriasCursando> materiashoy=new ArrayList<>();
    Retrofit retrofit = RetrofitClient.getClient();

    ApiService apiService = retrofit.create(ApiService.class);

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public principalFragment() {
        // Required empty public constructor
    }
    public static principalFragment newInstance(String param1, String param2) {
        principalFragment fragment = new principalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Fragment","ENTRO");
        Bundle arg= getArguments();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
          //  materiashoy=getArguments().getParcelable("materiasHoy");

        }
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
       // sideSheetContainer = v.findViewById(R.id.sideSheetContainer);
        //extendedFloatingActionButton = v.findViewById(R.id.fab);
    }
}
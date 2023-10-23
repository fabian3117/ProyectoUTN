package com.example.miutn;

import static com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.example.miutn.adapters.AdapterAgregaMateria;
import com.example.miutn.adapters.AdapterMisMaterias;
import com.example.miutn.enums.Carreras;
import com.example.miutn.enums.Cuatrimestres;
import com.example.miutn.enums.Sedes;
import com.example.miutn.fragment.misMateriasFragment;
import com.example.miutn.fragment.perfilFragment;
import com.example.miutn.fragment.principalFragment;
import com.example.miutn.network.api.ApiService;
import com.example.miutn.network.api.RetrofitClient;
import com.example.miutn.network.models.FechasExamenes;
import com.example.miutn.network.models.Materia;
import com.example.miutn.network.models.MateriasCursando;
import com.example.miutn.network.models.Profile;
import com.example.miutn.network.models.Temario;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miutn.databinding.ActivityMainBinding;
import com.google.android.material.search.SearchBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import android.widget.FrameLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    //TODO : MODIFICAR SEGURIDAD PARA UTILIZAR HTTPS

    private ActivityMainBinding binding;
    Snackbar snackbar;
    principalFragment fragment= new principalFragment();
    misMateriasFragment fragmentMisMat= new misMateriasFragment();
    perfilFragment framentProfile= new perfilFragment();

    FrameLayout sideSheetContainer;
    Retrofit retrofit = RetrofitClient.getClient();
    ExtendedFloatingActionButton extendedFloatingActionButton;
    ApiService apiService = retrofit.create(ApiService.class);
    BottomSheetDialog sideSheetDialog;
    Profile profile=new Profile();
    ArrayList<Materia> materiasCursadas=new ArrayList<>();
    ArrayList<Materia> programaAnal=new ArrayList<>();  //-->   Todas las materias de mi carrera    <---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        linkElement();
        CargaFragment();
        View v=findViewById(R.id.ParentView);
        snackbar= Snackbar.make(v,"",Snackbar.LENGTH_LONG);
        CargaInicialDatos();

        binding.tabSelector.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0: //-->   Tocamos Principal   <--
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPrincipalx,fragment).commit();
                        break;

                    case 1: //-->   Tocamos Mis materias    <--


                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPrincipalx,fragmentMisMat).commit();
                        break;
                    case 2: //-->   Tocamos Perfil  <--

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPrincipalx,framentProfile).commit();

                        break;
                    default:
                        break;
}
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        Call<ArrayList<Materia>> programa= apiService.obtenerMateriasProgramaAnal(Carreras.Electronica.getValorAsociado());
        programa.enqueue(new Callback<ArrayList<Materia>>() {
            @Override
            public void onResponse(Call<ArrayList<Materia>> call, Response<ArrayList<Materia>> response) {
                if(response.isSuccessful()){
                    programaAnal=response.body();
                    programaAnal.forEach(materia -> {
                        Log.e("PRIMER",materia.getNombre());
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Materia>> call, Throwable t) {

            }
        });
        Call<ArrayList<Materia>> obtenerMateriasCursadas=apiService.obtenerMateriasCursadas("FEDE");
        obtenerMateriasCursadas.enqueue(new Callback<ArrayList<Materia>>() {
            @Override
            public void onResponse(Call<ArrayList<Materia>> call, Response<ArrayList<Materia>> response) {
                if(response.isSuccessful()){
                    materiasCursadas=response.body();
                    materiasCursadas.forEach(materia -> {
                        Log.e("MIRAs",materia.getNombre());
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Materia>> call, Throwable t) {

            }
        });

    }
public void CargaInicialDatos(){
        //-->   Tenemos internet?   <--
    if(ConexionInternetDisponible()){
        //-->   Verifico datos en SharedPreference  <--
        //-->   Verifico ObtenerRecomendaciones     <---
        Log.e("ENTRO","CARGA");
        ArrayList<Temario> recomendaciones=ControlDatos.ObtenerRecomendaciones(this);
        ArrayList<FechasExamenes> fechasExamenes=ControlDatos.ObtencionFechasExamenes(this);
        ArrayList<MateriasCursando> materiasCursando=ControlDatos.ObtencionObtenerMateriasCursando(this);
        profile=ControlDatos.ObtencionPerfil(this);
        //-->   Si no esta vacio lo tengo que mostrar en el fragment principal por que esta opcion se ejecutara mas rapido de lo que cambio de fragment <--
        //-->   TODO Añadir un splash a fin de garantizar esto  <--
        if(recomendaciones!=null){
            fragment.ActualizacionDatosContenidosAdapterTemario(recomendaciones);
        }
        if(!fechasExamenes.isEmpty()){
            fragment.ActualizacionDatosContenidosAdapterFechasEx(fechasExamenes);
        }
        else{
            //-->   Cargo datos de pruebas  <--
            FechasExamenes fechasExamen=new FechasExamenes();
            String testFecha="18/10/2023";
            fechasExamen.setFecha(testFecha);
            Materia materia=new Materia();
            materia.setNombre("TEST FINAL");
            fechasExamen.setMateria(materia);
            ArrayList<FechasExamenes>fechasExamenes1=new ArrayList<>();
            fechasExamenes1.add(fechasExamen);
            fechasExamenes1.add(fechasExamen);
            ControlDatos.GuardarFechasExamenes(this,fechasExamenes1);
            fragment.ActualizacionDatosContenidosAdapterFechasEx(fechasExamenes1);
        }
        if(!materiasCursando.isEmpty()){
            //-->   Tengo que verificar cuales materias de las que curso efectivamente se cursan hoy    <--
            String dia="Lunes"; //TODO MODIFICAR ESTO
            ArrayList<MateriasCursando> deHoy=new ArrayList<>();
            fragment.ActualizacionDatosContenidosAdapterMaterias(materiasCursando);
            for(MateriasCursando materiaHoyy : materiasCursando){
            if(materiaHoyy.getDia().equals(dia)){
                deHoy.add(materiaHoyy);
            }
            }
            fragment.ActualizacionDatosContenidosAdapterMaterias(materiasCursando);
            fragment.ActualizacionDatosContenidosAdapterMateriasHoy(deHoy);
        }
        else{
            //-->   Carga de datos de prueba    <--
            MateriasCursando materiasCursa=new MateriasCursando();
            MateriasCursando materiasCursa1=new MateriasCursando();
            materiasCursa.setHorario("T1");
            materiasCursa.setSede("Campus");
            materiasCursa.setAula("S55");
            Materia materia=new Materia();
            Materia materia1=new Materia();
            materia1.setNombre("INFORMATICA 1");
            materia1.setAnio(1);
            materia.setNombre("Fisica 1");
            materia.setCuatri(Cuatrimestres.PrimerCuatrimestre.getValorAsociado());
            materia1.setCuatri(Cuatrimestres.PrimerCuatrimestre.getValorAsociado());
            materiasCursa1.setMateria(materia1);
            materiasCursa1.setDia("Martes");
            materiasCursa.setDia("Lunes");
            materiasCursa.setMateria(materia);
            ArrayList<MateriasCursando> buf=new ArrayList<>();
            buf.add(materiasCursa);
            buf.add(materiasCursa1);
            ControlDatos.GuardarMateriasCursando(this,buf);
        }
        if(profile.getName().isEmpty()){
            //-->   Cargo datos test    <--
            profile.setCarrera(Carreras.Electronica.getValorAsociado());
            profile.setName("Federico Gonzalez");
            profile.setCorreoInstitucional("fabian3117@frba.utn.edu.ar");
            profile.setNumberLegajo("1648408");
            profile.setUserSIU("fabian3117");
            ArrayList<MateriasCursando> materiasCursadas=new ArrayList<>();
            MateriasCursando test=new MateriasCursando();
            Materia materia=new Materia();
            test.setDia("Martes");
            test.setAula("S55");
            materia.setNombre("F1");
            test.setMateria(materia);
            test.setSede(Sedes.Campus.getValorAsociado());
            materiasCursadas.add(test);
            profile.setMateriasCursandos(materiasCursadas);
            profile.setMateriasCursadasAprobadas(materiasCursadas);
            ControlDatos.GuardarProfile(this,profile);
        }
        framentProfile.ActualizacionDatosContenidosAdapterProfile(profile);
    }
    else {
        //-->   TODO Revisar funcion de verificacion de internet y este orden de codigo esta invertido  <--
        //--> En caso de tener internet <--
        //-->   Hilo y a consultar API  <--

    }


}
public boolean ConexionInternetDisponible(){
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivityManager != null) {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    return false;
}


public void linkElement(){

    sideSheetContainer=findViewById(R.id.sideSheetContainer);
    extendedFloatingActionButton=findViewById(R.id.fab);

//TODO Terminar esto que es para añadir mas materias
    //Tengo que  Mostrar todas las materias posibles horarios y sedes

    extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LayoutInflater inflater=LayoutInflater.from(v.getContext());
            View bottomSheetView = inflater.inflate(R.layout.side_new_class, null);
            Chip selectorHoursInit=bottomSheetView.findViewById(R.id.chipClassInit);
            selectorHoursInit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialTimePicker picker =
                            new MaterialTimePicker.Builder()
                                    .setTimeFormat(TimeFormat.CLOCK_24H)
                                    .setHour(12)
                                    .setMinute(10)
                                    .setTitleText("Hora Inicio")
                                    .setInputMode(INPUT_MODE_CLOCK)
                                    .build();
                    picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("mira","HS:MM "+picker.getHour()+":"+picker.getMinute());
                            selectorHoursInit.setText("Inicio "+picker.getHour()+":"+picker.getMinute());
                        }
                    });
                    picker.show(getSupportFragmentManager(),"tag");
                }
            });
            Chip chipClassFinish=bottomSheetView.findViewById(R.id.chipClassFinish);
            chipClassFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialTimePicker picker =
                            new MaterialTimePicker.Builder()
                                    .setTimeFormat(TimeFormat.CLOCK_24H)
                                    .setHour(12)
                                    .setMinute(10)
                                    .setTitleText("Hora fin")
                                    .setInputMode(INPUT_MODE_CLOCK)
                                    .build();
                    picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("mira","HS:MM "+picker.getHour()+":"+picker.getMinute());
                            chipClassFinish.setText("Fin "+picker.getHour()+":"+picker.getMinute());
                        }
                    });
                    picker.show(getSupportFragmentManager(),"tag");
                }
            });
            bottomSheetView.findViewById(R.id.buttonSaveNewClass).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Toco","GUARDAR");
                    sideSheetDialog.dismiss();
                    snackbar.setText("Guardando");
                    //-->   Recolectar datos y guardar  <--
                    snackbar.show();
                }
            });
            RecyclerView recyclerView=bottomSheetView.findViewById(R.id.listaMateriaPuedeCursar);
            ArrayList<String> test=new ArrayList<>();
            test.add("INFO1");
            test.add("AM");
            test.add("AM");
            AdapterAgregaMateria adapterAgregaMateria=new AdapterAgregaMateria(PuedoCursar(materiasCursadas,programaAnal));
            recyclerView.setAdapter(adapterAgregaMateria);
            recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
            sideSheetDialog=new BottomSheetDialog(MainActivity.this);
            sideSheetDialog.setContentView(bottomSheetView);
//            sideSheetDialog.setContentView(R.layout.side_new_class);
            sideSheetDialog.show();
        }
    });
}
public ArrayList<String> PuedoCursar(ArrayList<Materia> misMaterias, ArrayList<Materia> programaAnal){
        ArrayList<String> salida=new ArrayList<>();
       //-->    Va a procesar el servidor no yo...
    salida.add("Informatica 1");
    salida.add("AGA");
    salida.add("AM 1");
    salida.add("AM 2");
        return salida;
}
public void CargaFragment(){

    getSupportFragmentManager().beginTransaction().add(R.id.fragmentPrincipalx,fragment).commit();

}
}
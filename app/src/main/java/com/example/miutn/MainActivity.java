package com.example.miutn;

import static com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.example.miutn.adapters.AdapterAgregaMateria;
import com.example.miutn.adapters.AdapterMisMaterias;
import com.example.miutn.enums.Carreras;
import com.example.miutn.enums.Cuatrimestres;
import com.example.miutn.enums.Modalidad;
import com.example.miutn.enums.Sedes;
import com.example.miutn.fragment.misMateriasFragment;
import com.example.miutn.fragment.perfilFragment;
import com.example.miutn.fragment.principalFragment;
import com.example.miutn.network.api.ApiService;
import com.example.miutn.network.api.RetrofitClient;
import com.example.miutn.network.models.FechasExamenes;
import com.example.miutn.network.models.Horarios;
import com.example.miutn.network.models.Materia;
import com.example.miutn.network.models.MateriasCursando;
import com.example.miutn.network.models.NMateria;
import com.example.miutn.network.models.NMateriasCursando;
import com.example.miutn.network.models.NprogramaAnalitico;
import com.example.miutn.network.models.Perfil;
import com.example.miutn.network.models.Profile;
import com.example.miutn.network.models.Temario;
import com.example.miutn.network.services.ManejadorNuevaMateria;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miutn.databinding.ActivityMainBinding;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import android.widget.FrameLayout;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    //TODO : MODIFICAR SEGURIDAD PARA UTILIZAR HTTPS

    private ActivityMainBinding binding;
    Snackbar snackbar;
    Perfil perfil=new Perfil();
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
        Intent intent= new Intent(this, MainActivity2.class);
        //startActivity(intent);
        linkElement();
        CargaFragment();
        View v=findViewById(R.id.ParentView);
        SearchBar views=findViewById(R.id.search_bar);
        SearchView view1=findViewById(R.id.SearchView);
        view1.setupWithSearchBar(views);
        snackbar= Snackbar.make(v,"",Snackbar.LENGTH_LONG);

        binding.lottieAnimationMajor.setVisibility(View.GONE);
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

        CargaInicialTest();

    }
    public void CargaInicialTest(){

        if(ConexionInternetDisponible()){
            Carreras ele=Carreras.Electronica;
            apiService.obtenerMateriasProgramaAnal(ele).enqueue(new Callback<ArrayList<NMateria>>() {
                @Override
                public void onResponse(Call<ArrayList<NMateria>> call, Response<ArrayList<NMateria>> response) {
                if(response.isSuccessful()){
                    for(NMateria materia: response.body()){
                        Log.e("MIRA",materia.getName());
                    }
                    ControlDatos.GuardarProgramaAnalitico(response.body(),getApplicationContext());
                    fragmentMisMat.ActualizacionDatosContenidosAdapterMisMaterias_Programa(response.body());
                }
                else{
                    Log.e("PROGrama anal","otro fallo");
                }
                }

                @Override
                public void onFailure(Call<ArrayList<NMateria>> call, Throwable t) {
                Log.e("Error programa Anal",t.getMessage());
                }
            });

            apiService.descargaPerfil("TS").enqueue(new Callback<Perfil>() {
                @Override
                public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                    if(response.isSuccessful()){
                        perfil=response.body();
                        ControlDatos.GuardarProfile(getApplicationContext(),profile);
                        ObtencionMateriasHoy(perfil);
                    }
            else {
                snackbar.setText("Error en obtencion de datos de servidor");
                snackbar.show();
            }
            fragment.ActualizacionDatosContenidosAdapterMaterias(perfil.getMateriasCursando());
            ArrayList<NMateria> analiticos=ControlDatos.ObtencionProgramaAnalitico(getApplicationContext());
            ArrayList<NMateriasCursando> example=new ArrayList<>();

            fragmentMisMat.ActualizacionDatosContenidosAdapterMisMaterias(analiticos,perfil.getMateriasCursando());
            framentProfile.ActualizacionDatosContenidosAdapterProfile(perfil);
                }
                @Override
                public void onFailure(Call<Perfil> call, Throwable t) {
                    Log.e("ERORR CONEXION WEB",t.getMessage());
                    snackbar.setText("Error en conexion a servidor verificar");
                    snackbar.show();
                    perfil=ControlDatos.ObtenerPerfil(getApplicationContext());
                    ObtencionMateriasHoy(perfil);
                    framentProfile.ActualizacionDatosContenidosAdapterProfile(perfil);
                    fragment.ActualizacionDatosContenidosAdapterMaterias(perfil.getMateriasCursando());
                }
            });
        }
        else if(!ControlDatos.ExistePerfil(getApplicationContext())){
            //-->   No tenemos nada <--
            //-->   Pantalla Inicio seccion <--
            Intent intent=new Intent(getApplicationContext(), Login.class);
            getApplicationContext().startActivity(intent);
            finish();   //-->   Finalizo esta actividad <--
        }
        ArrayList<Temario> recomendaciones=ControlDatos.ObtenerRecomendaciones(this);
        ArrayList<FechasExamenes> fechasExamenes=ControlDatos.ObtencionFechasExamenes(this);
        fragment.ActualizacionDatosContenidosAdapterTemario(recomendaciones);
        fragment.ActualizacionDatosContenidosAdapterFechasEx(fechasExamenes);
    }
    public void ObtencionMateriasHoy(Perfil perfil){
        Calendar calendar=Calendar.getInstance();
        String dia = new SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.getTime());
        ArrayList<NMateriasCursando> deHoy=new ArrayList<>();
        for(NMateriasCursando materiaHoyy : perfil.getMateriasCursando()){
           /* if(materiaHoyy.getHorario().getDia().equals(dia)){
                deHoy.add(materiaHoyy);
            }*/
        }
        fragment.ActualizacionDatosContenidosAdapterMateriasHoy(deHoy);
    }
public void CargaInicialDatos(){
    //-->   Tenemos internet?   <--
    if(ConexionInternetDisponible()){
        //-->   Verifico datos en SharedPreference  <--
        //-->   Verifico ObtenerRecomendaciones     <---
        Log.e("ENTRO","CARGA");
        ArrayList<Temario> recomendaciones=ControlDatos.ObtenerRecomendaciones(this);
        ArrayList<FechasExamenes> fechasExamenes=ControlDatos.ObtencionFechasExamenes(this);
        ArrayList<NMateriasCursando> materiasCursando=ControlDatos.ObtencionObtenerMateriasCursando(this);
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
            ArrayList<NMateriasCursando> deHoy=new ArrayList<>();
          //  fragment.ActualizacionDatosContenidosAdapterMaterias(materiasCursando);
            for(NMateriasCursando materiaHoyy : materiasCursando){
            if(materiaHoyy.getHorario().getDia().equals(dia)){
                deHoy.add(materiaHoyy);
            }
            }
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
//        framentProfile.ActualizacionDatosContenidosAdapterProfile(profile);
    }
    else {
        //-->   TODO Revisar funcion de verificacion de internet y este orden de codigo esta invertido  <--
        //--> En caso de tener internet <--
        //-->   Hilo y a consultar API  <--

    }


}
public boolean ConexionInternetDisponible(){
    ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
}
public void linkElement(){
    String horaInicio="";
    String horaFin="";
    sideSheetContainer=findViewById(R.id.sideSheetContainer);
    extendedFloatingActionButton=findViewById(R.id.fab);
    ArrayList<String> diasCursa=new ArrayList<>();
//TODO Terminar esto que es para añadir mas materias
    extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LayoutInflater inflater=LayoutInflater.from(v.getContext());
            View bottomSheetView = inflater.inflate(R.layout.side_new_class, null);
            Chip selectorHoursInit=bottomSheetView.findViewById(R.id.chipClassInit);
            //selectorHoursInit.setOnCheckedChangeListener(new ConfiguracionRelojes(selectorHoursInit,getSupportFragmentManager()));
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
                          //  horaInicio=""+picker.getHour()+":"+picker.getMinute();
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
                    sideSheetDialog.dismiss();
                    snackbar.setText("Guardando");
                    //-->   Recolectar datos y guardar  <--
                    Chip campus= bottomSheetView.findViewById(R.id.chipNewClassCampus);
                    Chip medrano= bottomSheetView.findViewById(R.id.chipNewClassMedrano);
                    String sedeCursa=(campus.isChecked()?"Campus":(medrano.isChecked()?"Medrano":""));
                    Log.e("Toco","GUARDAR : "+sedeCursa);
                    String Dia;
                    ChipGroup chipGroup=bottomSheetView.findViewById(R.id.chipGroupDiaNewClass);
                    if(chipGroup.getCheckedChipIds().isEmpty()){
                        snackbar.setText("Error Ingresar dia");
                        snackbar.show();
                        return;
                    }
                    for( Integer dk :chipGroup.getCheckedChipIds()){
                        String d="";
                        switch (dk){
                            case 1:
                                d="Lunes";
                                break;
                            case 2:
                                d="Martes";
                                break;
                            case 3:
                                d="Miercoles";
                                break;
                            case 4:
                                d="Jueves";
                                break;
                            case 5:
                                d="Vierenes";
                                break;
                            case 6:
                                d="Sabado";
                                break;
                        }
                        Log.e("CAMBIO OPCION", d);
                        diasCursa.add(d);
                    }
                    String horaIn=selectorHoursInit.getText().toString().replace("Inicio ","");
                    String horaFn=chipClassFinish.getText().toString().replace("Fin ","");
                    RecyclerView view=bottomSheetView.findViewById(R.id.listaMateriaPuedeCursar);
                    AdapterAgregaMateria adapterAgregaMateria=(AdapterAgregaMateria) view.getAdapter();
                    String materiaName=adapterAgregaMateria.getMateriaSeleccionada();
                    Log.e("SALIDA",materiaName);
                    NMateria materia=new NMateria();
                    Horarios horario=new Horarios();
                    horario.setDia((!diasCursa.isEmpty())?diasCursa.get(0):"");
                    horario.setHoraInicio(horaIn);
                    horario.setHoraFin(horaFn);
                    materia.setName(materiaName);
                    NMateriasCursando toServer=new NMateriasCursando();
                    toServer.setHorario(horario);
                    toServer.setMateria(materia);
                   // TODO Falta enviar a servidor y guardar    <---
                        //-->   Esto deberia ir en segundo plano

                    apiService.guardarNuevaMateria("NUEVO",toServer).enqueue( new ControlaGuardado(getApplicationContext()));


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
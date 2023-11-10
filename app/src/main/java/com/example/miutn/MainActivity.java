package com.example.miutn;

import static com.example.miutn.enums.Carreras.Electronica;
import static com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miutn.adapters.AdapterAgregaMateria;
import com.example.miutn.databinding.ActivityMainBinding;
import com.example.miutn.enums.Carreras;
import com.example.miutn.fragment.misMateriasFragment;
import com.example.miutn.fragment.perfilFragment;
import com.example.miutn.fragment.principalFragment;
import com.example.miutn.network.api.ApiService;
import com.example.miutn.network.api.RetrofitClient;
import com.example.miutn.network.models.FechasExamenes;
import com.example.miutn.network.models.Horarios;
import com.example.miutn.network.models.Materia;
import com.example.miutn.network.models.NMateria;
import com.example.miutn.network.models.NMateriasCursando;
import com.example.miutn.network.models.Perfil;
import com.example.miutn.network.models.Profile;
import com.example.miutn.network.models.Temario;
import com.example.miutn.utils.General;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/** @noinspection ALL */
public class MainActivity extends AppCompatActivity {
    //TODO : MODIFICAR SEGURIDAD PARA UTILIZAR HTTPS

    private ActivityMainBinding binding;
    Snackbar snackbar;
    Perfil perfil = new Perfil();
    principalFragment fragment = new principalFragment();
    static final String ProfileID = "TS";
    misMateriasFragment fragmentMisMat = new misMateriasFragment();
    perfilFragment framentProfile = new perfilFragment();
    ArrayList<FechasExamenes> fechasExamenes = new ArrayList<>();

    /** @noinspection unused*/
    FrameLayout sideSheetContainer;
    Retrofit retrofit = RetrofitClient.getClient();
    public final static String carreraSelect="Electronica";
    ExtendedFloatingActionButton extendedFloatingActionButton;
    ApiService apiService = retrofit.create(ApiService.class);
    BottomSheetDialog sideSheetDialog;
    Profile profile = new Profile();
    ArrayList<Materia> materiasCursadas = new ArrayList<>();
    ArrayList<Materia> programaAnal = new ArrayList<>();  //-->   Todas las materias de mi carrera    <---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // Intent intent = new Intent(this, MainActivity2.class);
        //startActivity(intent);
        linkElement();
        CargaFragment();
        View v = findViewById(R.id.ParentView);
        SearchBar views = findViewById(R.id.search_bar);
        SearchView view1 = findViewById(R.id.SearchView);
        view1.setupWithSearchBar(views);
        snackbar = Snackbar.make(v, "", Snackbar.LENGTH_LONG);

        binding.lottieAnimationMajor.setVisibility(View.GONE);
        binding.tabSelector.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: //-->   Tocamos Principal   <--
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPrincipalx, fragment).commit();
                        break;

                    case 1: //-->   Tocamos Mis materias    <--
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPrincipalx, fragmentMisMat).commit();
                        break;
                    case 2: //-->   Tocamos Perfil  <--
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPrincipalx, framentProfile).commit();
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
        Carreras el= Carreras.valueOf("Electronica");

       /* apiService.materiasAsociadas(carreraSelect).enqueue(new Callback<ArrayList<NMateria>>() {
            @Override
            public void onResponse(Call<ArrayList<NMateria>> call, Response<ArrayList<NMateria>> response) {
                if(response.isSuccessful()){
                    Log.e("SOLICITUD","EXITO");
                    ControlDatos.GuardarProgramaAnalitico(response.body(),getApplicationContext());
                    fragmentMisMat.ActualizacionDatosContenidosAdapterMisMaterias_Programa(response.body());

                }
                else{
                    Log.e("SOLICITUD","FALLO");
                    fragmentMisMat.ActualizacionDatosContenidosAdapterMisMaterias_Programa(ControlDatos.ObtencionProgramaAnalitico(getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NMateria>> call, Throwable t) {
                Log.e("SOLICITUD",t.getMessage());
                snackbar.setText("error Obtencion datos");
                snackbar.show();
            }
        });
*/
    }

    public void CargaInicialTest() {

        if (ConexionInternetDisponible()) {
            apiService.obtenerMateriasProgramaAnal(Electronica).enqueue(new Callback<ArrayList<NMateria>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<NMateria>> call, @NonNull Response<ArrayList<NMateria>> response) {
                    if (response.isSuccessful()) {
                        ControlDatos.GuardarProgramaAnalitico(response.body(), getApplicationContext());
                        fragmentMisMat.ActualizacionDatosContenidosAdapterMisMaterias_Programa(response.body());
                    } else {
                        Log.e("Programa Analitico", "otro fallo");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<NMateria>> call, @NonNull Throwable t) {
                    Log.e("Error programa Anal", Objects.requireNonNull(t.getMessage()));
                }
            });
            apiService.obtenerProximasFechas().enqueue(new Callback<ArrayList<FechasExamenes>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<FechasExamenes>> call, @NonNull Response<ArrayList<FechasExamenes>> response) {
                    if (response.isSuccessful()) {
                        fechasExamenes = response.body();
                        ControlDatos.GuardarFechasExamenes(getApplicationContext(), fechasExamenes);
                        fragment.ActualizacionDatosContenidosAdapterFechasEx(fechasExamenes);
                        assert response.body() != null;
                        for (FechasExamenes materia : response.body()) {
                            Log.e("FECHA", materia.getMateria().getName());
                        }
                        General.fechasExamenes=fechasExamenes;
                    } else {
                        snackbar.setText("Error en obtencion fechas de examen");
                        snackbar.show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<FechasExamenes>> call, @NonNull Throwable t) {
                    fechasExamenes = ControlDatos.ObtencionFechasExamenes(getApplicationContext());
                    General.fechasExamenes=fechasExamenes;
                    fragment.ActualizacionDatosContenidosAdapterFechasEx(fechasExamenes);
                }
            });

            apiService.descargaPerfil(ProfileID).enqueue(new Callback<Perfil>() {
                @Override
                public void onResponse(@NonNull Call<Perfil> call, @NonNull Response<Perfil> response) {
                    if (response.isSuccessful()) {
                        perfil = response.body();
                        ControlDatos.GuardarProfile(getApplicationContext(), profile);
                        ObtencionMateriasHoy(perfil);
                        General.perfil=perfil;
                    } else {
                        snackbar.setText("Error en obtencion de datos de servidor");
                        snackbar.show();
                    }
                    fragment.ActualizacionDatosContenidosAdapterMaterias(perfil.getMateriasCursando());
                    ArrayList<NMateria> analiticos = ControlDatos.ObtencionProgramaAnalitico(getApplicationContext());
                    fragmentMisMat.ActualizacionDatosContenidosAdapterMisMaterias(analiticos, perfil.getMateriasCursando());
                    framentProfile.ActualizacionDatosContenidosAdapterProfile(perfil);
                }

                @Override
                public void onFailure(@NonNull Call<Perfil> call,@NonNull Throwable t) {
                    Log.e("ERORR CONEXION WEB", Objects.requireNonNull(t.getMessage()));
                    snackbar.setText("Error en conexion a servidor verificar");
                    snackbar.show();
                    perfil = ControlDatos.ObtenerPerfil(getApplicationContext());
                    ObtencionMateriasHoy(perfil);
                    framentProfile.ActualizacionDatosContenidosAdapterProfile(perfil);
                    fragment.ActualizacionDatosContenidosAdapterMaterias(perfil.getMateriasCursando());
                }
            });
        } else if (!ControlDatos.ExistePerfil(getApplicationContext())) {
            //-->   No tenemos nada <--
            //-->   Pantalla Inicio seccion <--
            Intent intent = new Intent(getApplicationContext(), Login.class);
            getApplicationContext().startActivity(intent);
            finish();   //-->   Finalizo esta actividad <--
        }
        apiService.obtenerApuntes("FEDE").enqueue(new Callback<ArrayList<Temario>>() {
            @Override
            public void onResponse(Call<ArrayList<Temario>> call, Response<ArrayList<Temario>> response) {
                if(response.isSuccessful()){
                    ArrayList<Temario> recomendaciones = response.body();
                    ControlDatos.GuardarRecomendaciones(getApplicationContext(),recomendaciones);
                    ActualizaRecomendaciones(recomendaciones);
                    General.recomendaciones=recomendaciones;
                }
                else{
                    ArrayList<Temario> recomendaciones = ControlDatos.ObtenerRecomendaciones(getApplicationContext());
                    General.recomendaciones=recomendaciones;
                   ActualizaRecomendaciones(recomendaciones);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Temario>> call, Throwable t) {
                ArrayList<Temario> recomendaciones = ControlDatos.ObtenerRecomendaciones(getApplicationContext());
                General.recomendaciones=recomendaciones;
                ActualizaRecomendaciones(recomendaciones);
            }
        });
        //ArrayList<Temario> recomendaciones = ControlDatos.ObtenerRecomendaciones(this);
        //fragment.ActualizacionDatosContenidosAdapterTemario(recomendaciones);

    }
    public void ActualizaRecomendaciones(ArrayList<Temario> recomendacion){
        fragment.ActualizacionDatosContenidosAdapterTemario(recomendacion);
    }

    public void ObtencionMateriasHoy(Perfil perfil) {
        Calendar calendar = Calendar.getInstance();
        String dia = new SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.getTime());
        ArrayList<NMateriasCursando> deHoy = new ArrayList<>();
        for (NMateriasCursando materiaHoyy : perfil.getMateriasCursando()) {
            if (materiaHoyy.getHorario().getDia().equals(dia)) {
                deHoy.add(materiaHoyy);
            }
        }
        fragment.ActualizacionDatosContenidosAdapterMateriasHoy(deHoy);
    }

    public boolean ConexionInternetDisponible() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @SuppressLint("SetTextI18n")
    public void linkElement() {
        sideSheetContainer = findViewById(R.id.sideSheetContainer);
        extendedFloatingActionButton = findViewById(R.id.fab);
        ArrayList<String> diasCursa = new ArrayList<>();
//TODO Terminar esto que es para añadir mas materias
        extendedFloatingActionButton.setOnClickListener(v -> {
            LayoutInflater inflater = LayoutInflater.from(v.getContext());
            @SuppressLint("InflateParams") View bottomSheetView = inflater.inflate(R.layout.side_new_class, null);
            Chip selectorHoursInit = bottomSheetView.findViewById(R.id.chipClassInit);
            //selectorHoursInit.setOnCheckedChangeListener(new ConfiguracionRelojes(selectorHoursInit,getSupportFragmentManager()));
            selectorHoursInit.setOnClickListener(v1 -> {
                MaterialTimePicker picker =
                        new MaterialTimePicker.Builder()
                                .setTimeFormat(TimeFormat.CLOCK_24H)
                                .setHour(12)
                                .setMinute(10)
                                .setTitleText("Hora Inicio")
                                .setInputMode(INPUT_MODE_CLOCK)
                                .build();
                picker.addOnPositiveButtonClickListener(v11 -> selectorHoursInit.setText("Inicio " + picker.getHour() + ":" + picker.getMinute()));
                picker.show(getSupportFragmentManager(), "tag");
            });
            Chip chipClassFinish = bottomSheetView.findViewById(R.id.chipClassFinish);
            chipClassFinish.setOnClickListener(v12 -> {
                MaterialTimePicker picker =
                        new MaterialTimePicker.Builder()
                                .setTimeFormat(TimeFormat.CLOCK_24H)
                                .setHour(12)
                                .setMinute(10)
                                .setTitleText("Hora fin")
                                .setInputMode(INPUT_MODE_CLOCK)
                                .build();
                picker.addOnPositiveButtonClickListener(v121 ->
                        chipClassFinish.setText("Fin " + picker.getHour() + ":" + picker.getMinute()));
                picker.show(getSupportFragmentManager(), "tag");
            });
            bottomSheetView.findViewById(R.id.buttonSaveNewClass).setOnClickListener(v13 -> {
                sideSheetDialog.dismiss();
                snackbar.setText("Guardando");
                //-->   Recolectar datos y guardar  <--
                ChipGroup chipGroup = bottomSheetView.findViewById(R.id.chipGroupDiaNewClass);
                if (chipGroup.getCheckedChipIds().isEmpty()) {
                    snackbar.setText("Error Ingresar dia");
                    snackbar.show();
                    return;
                }
                for (Integer dk : chipGroup.getCheckedChipIds()) {
                    String d = "";
                    switch (dk) {
                        case 1:
                            d = "Lunes";
                            break;
                        case 2:
                            d = "Martes";
                            break;
                        case 3:
                            d = "Miercoles";
                            break;
                        case 4:
                            d = "Jueves";
                            break;
                        case 5:
                            d = "Vierenes";
                            break;
                        case 6:
                            d = "Sabado";
                            break;
                    }
                    Log.e("CAMBIO OPCION", d);
                    diasCursa.add(d);
                }
                String horaIn = selectorHoursInit.getText().toString().replace("Inicio ", "");
                String horaFn = chipClassFinish.getText().toString().replace("Fin ", "");
                RecyclerView view = bottomSheetView.findViewById(R.id.listaMateriaPuedeCursar);
                AdapterAgregaMateria adapterAgregaMateria = (AdapterAgregaMateria) view.getAdapter();
                assert adapterAgregaMateria != null;
                String materiaName = adapterAgregaMateria.getMateriaSeleccionada();
                NMateria materia = new NMateria();
                Horarios horario = new Horarios();
                horario.setDia((!diasCursa.isEmpty()) ? diasCursa.get(0) : "");
                horario.setHoraInicio(horaIn);
                horario.setHoraFin(horaFn);
                materia.setName(materiaName);
                NMateriasCursando toServer = new NMateriasCursando();
                toServer.setHorario(horario);
                toServer.setMateria(materia);
                apiService.guardarNuevaMateria("NUEVO", toServer).enqueue(new ControlaGuardado(getApplicationContext()));
                snackbar.show();
            });
            RecyclerView recyclerView = bottomSheetView.findViewById(R.id.listaMateriaPuedeCursar);
            AdapterAgregaMateria adapterAgregaMateria = new AdapterAgregaMateria(PuedoCursar(materiasCursadas, programaAnal));
            recyclerView.setAdapter(adapterAgregaMateria);
            recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
            sideSheetDialog = new BottomSheetDialog(MainActivity.this);
            sideSheetDialog.setContentView(bottomSheetView);
            sideSheetDialog.show();
        });
    }

    /** @noinspection unused, unused */
    public ArrayList<String> PuedoCursar(ArrayList<Materia> misMaterias, ArrayList<Materia> programaAnal) {
        ArrayList<String> salida = new ArrayList<>();
        //-->    Va a procesar el servidor no yo...
        salida.add("Informatica 1");
        salida.add("AGA");
        salida.add("AM 1");
        salida.add("AM 2");
        return salida;
    }

    public void CargaFragment() {

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentPrincipalx, fragment).commit();

    }
}
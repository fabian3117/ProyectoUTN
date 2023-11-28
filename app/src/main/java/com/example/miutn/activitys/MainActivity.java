package com.example.miutn.activitys;

import static com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.RemoteViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miutn.ControlDatos;
import com.example.miutn.ControlaGuardado;
import com.example.miutn.R;
import com.example.miutn.databinding.ActivityMainBinding;
import com.example.miutn.network.api.ApiService;
import com.example.miutn.fragment.*;
import com.example.miutn.network.api.RetrofitClient;

import com.example.miutn.network.models.*;
import com.example.miutn.notifications.Notificaciones;
import com.example.miutn.utils.General;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Retrofit;

/** @noinspection CanBeFinal, CanBeFinal, CanBeFinal, CanBeFinal, CanBeFinal */
public class MainActivity extends AppCompatActivity {
    //TODO : MODIFICAR SEGURIDAD PARA UTILIZAR HTTPS
    //TODO HICE AL REVES -> OBTENER DATOS EN SHAREDPREFERENCE SI NO TENEMOS VAMOS A LOGIN - ACTUALIZAMOS IGUAL  <--
    private ActivityMainBinding binding;
    Snackbar snackbar;
    Perfil perfil = new Perfil();
    principalFragment fragment = new principalFragment();
    misMateriasFragment fragmentMisMat = new misMateriasFragment();
    perfilFragment framentProfile = new perfilFragment();
    ArrayList<FechasExamenes> fechasExamenes = new ArrayList<>();

    FrameLayout sideSheetContainer;
    ArrayList<NMateria> programaAnalitico = new ArrayList<>();
    Retrofit retrofit = RetrofitClient.getClient();
    ApiService apiService = retrofit.create(ApiService.class);
    BottomSheetDialog sideSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //contenedorProximasFechas = findViewById(R.id.contenedorProximasFechas);
        linkElement();
        CargaFragment();
        binding.SearchView.setupWithSearchBar(binding.searchBar);
        binding.SearchView.getEditText().setOnEditorActionListener((v1, actionId, event) -> {
            binding.SearchView.hide();
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH
                    || (event != null && event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                //TODO VERIFICAR POR QUE ENTRA 2 VECESc
                Log.e("ENTRO POR ENTER", String.valueOf(v1.getText()));

            }
            return false;
        });
        Notificaciones notificaciones= new Notificaciones();
        notificaciones.showNotification(getApplicationContext(), "titulo", "contenido");
        snackbar = Snackbar.make(binding.ParentView, "", Snackbar.LENGTH_LONG);

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

    }

    public void CargaInicialTest() {
        perfil = ControlDatos.ObtenerPerfil(getApplicationContext());
        //-->   Tener un ID vacio implica que no tenemos datos entonces vamos a login   <--
        if (perfil.getId().isEmpty()) {
            //--->  Descarga toda la informacion de la web  <--
            snackbar.setText("Error No informacion en app");
            snackbar.show();
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        } else {
            fechasExamenes = ControlDatos.ObtencionFechasExamenes(getApplicationContext());
            programaAnalitico = ControlDatos.ObtencionProgramaAnalitico(getApplicationContext());
            ArrayList<Temario> recomendaciones = ControlDatos.ObtenerRecomendaciones(getApplicationContext());
            //-->   Añado recomendaciones falzas   <--<--
            //todo añadir recomendaciones y incluir ciclo de actualizacion  <--
            Temario tema = new Temario();
            tema.setApunte("ts");
            tema.setDescription("Sss");
            tema.setTema("aaaa");
            tema.setId("prueba.md");
            Temario tema0 = new Temario();
            tema0.setApunte("Segundo");
            tema0.setDescription("DEscrip");
            tema0.setTema("orueba");
            tema0.setId("probandomark.md");
            recomendaciones.add(tema);
            recomendaciones.add(tema0);
            ArrayList<NMateriasCursando> materiasCursando = ControlDatos.ObtencionObtenerMateriasCursando(getApplicationContext());
//todo utilizar programa analitico para llenar el sidesheet
            //-->   Actualizaciones de fragment <--
            ObtencionMateriasHoy(perfil);
            fragmentMisMat.ActualizacionDatosContenidosAdapterMisMaterias_Programa(programaAnalitico);
            fragment.ActualizacionDatosContenidosAdapterFechasEx(fechasExamenes);
            fragment.ActualizacionDatosContenidosAdapterMaterias(perfil.getMateriasCursando());
            ArrayList<NMateria> analiticos = ControlDatos.ObtencionProgramaAnalitico(getApplicationContext());
            fragmentMisMat.ActualizacionDatosContenidosAdapterMisMaterias(analiticos, perfil.getMateriasCursando());
            framentProfile.ActualizacionDatosContenidosAdapterProfile(perfil);
            ActualizaRecomendaciones(recomendaciones);
        }
    }

    public void ActualizaRecomendaciones(ArrayList<Temario> recomendacion) {
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
        if(!deHoy.isEmpty()){
            fragment.ActualizacionDatosContenidosAdapterMateriasHoy(deHoy);
        }


    }

    public boolean ConexionInternetDisponible() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @SuppressLint("SetTextI18n")
    public void linkElement() {
        ArrayList<String> diasCursa = new ArrayList<>();
//TODO Terminar esto que es para añadir mas materias
        binding.fab.setOnClickListener(v -> {
            LayoutInflater inflater = LayoutInflater.from(v.getContext());
            @SuppressLint("InflateParams") View bottomSheetView = inflater.inflate(R.layout.side_new_class, null);
            Chip selectorHoursInit = bottomSheetView.findViewById(R.id.chipClassInit);
            ChipGroup pruebaChipgroup=bottomSheetView.findViewById(R.id.newClassChipGroup);
            Snackbar snackbar1=Snackbar.make(v.getRootView(),"ASS",Snackbar.LENGTH_LONG);
            snackbar1.getView().setElevation(800f);
            snackbar1.show();
            ArrayList<NMateria> paraCursar=General.puedoCursarNuevaVersion(programaAnalitico, perfil.getMateriasCursadas());
            for(NMateria materia:paraCursar){
                Chip chip=new Chip(v.getContext());
                chip.setText(materia.getName());
                chip.setCheckable(true);
                pruebaChipgroup.addView(chip);
            }

            selectorHoursInit.setOnClickListener(v1 -> {

                MaterialTimePicker picker =
                        new MaterialTimePicker.Builder()
                                .setTimeFormat(TimeFormat.CLOCK_24H)
                                .setHour(12)
                                .setTitleText("Hora Inicio")
                                .setInputMode(INPUT_MODE_CLOCK)
                                .build();

                //MaterialTimePicker picker=General.picker;
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
                    String d = obtenerDia(dk);
                    Log.e("CAMBIO OPCION", d);
                    diasCursa.add(d);
                }
                String horaIn = selectorHoursInit.getText().toString().replace("Inicio ", "");
                String horaFn = chipClassFinish.getText().toString().replace("Fin ", "");
                RecyclerView view = bottomSheetView.findViewById(R.id.listaMateriaPuedeCursar);
                //-->   Mi error esta aca estoy obteniendo el chipGroup del dia <--
                @SuppressLint("CutPasteId") NMateria materia=General.chipSeleccionadas(bottomSheetView.findViewById(R.id.newClassChipGroup));
                Horarios horario = new Horarios();
                //todo aca nos quedamos
                //@aca estamos falta modificar esto para que sea compatible con chips
                //todo Modificar el codigo para que actualize los datos usando la db    <--

                horario.setDia((!diasCursa.isEmpty()) ? diasCursa.get(0) : "");
                horario.setHoraInicio(horaIn);
                horario.setHoraFin(horaFn);
                NMateriasCursando toServer = new NMateriasCursando();
                toServer.setHorario(horario);
                toServer.setMateria(materia);
                apiService.guardarNuevaMateria(perfil.getId(), toServer).enqueue(new ControlaGuardado(getApplicationContext()));
                snackbar.show();
            });
            sideSheetDialog = new BottomSheetDialog(MainActivity.this);
            sideSheetDialog.setContentView(bottomSheetView);
            sideSheetDialog.show();
        });
    }
    public void CargaFragment() {

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentPrincipalx, fragment).commit();

    }
    public String obtenerDia(Integer dk){
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
        return d;
    }
    public NMateria chipSeleccionadas(ChipGroup chipGroup){
        NMateria salida=new NMateria();
        for(Integer i:chipGroup.getCheckedChipIds()){
            Chip checkedChip = chipGroup.findViewById(i);
            if(checkedChip!=null){
                salida.setId(String.valueOf(checkedChip.getId()));
                salida.setName(String.valueOf(checkedChip.getText()));
            }
        }
        return salida;
    }
}
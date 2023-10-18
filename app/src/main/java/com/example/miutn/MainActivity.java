package com.example.miutn;

import static com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;

import com.example.miutn.enums.Carreras;
import com.example.miutn.network.api.ApiService;
import com.example.miutn.network.api.RetrofitClient;
import com.example.miutn.network.models.Materia;
import com.example.miutn.network.models.MateriasCursando;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.sidesheet.SideSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miutn.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    //TODO : MODIFICAR SEGURIDAD PARA UTILIZAR HTTPS

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private TabLayout tabSelector;
    private RecyclerView RecyclerMismaterias;
    private RecyclerView RecyclerMismateriasHoy;
    AdapterMisMaterias adapterMisMaterias=new AdapterMisMaterias();
    AdapterMisMaterias adapterMisMateriasHoy=new AdapterMisMaterias();
    FrameLayout sideSheetContainer;
    Retrofit retrofit = RetrofitClient.getClient();
    ExtendedFloatingActionButton extendedFloatingActionButton;
    ApiService apiService = retrofit.create(ApiService.class);
    ArrayList<Materia> materiasCursadas=new ArrayList<>();
    ArrayList<Materia> programaAnal=new ArrayList<>();  //-->   Todas las materias de mi carrera    <---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        linkElement();
        CargaFragment();
        binding.tabSelector.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0: //-->   Tocamos Principal   <--
                        principalFragment fragment= new principalFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPrincipalx,fragment).commit();
                        break;

                    case 1: //-->   Tocamos Mis materias    <--
                        misMateriasFragment fragmentMisMat= new misMateriasFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPrincipalx,fragmentMisMat).commit();
                        break;
                    case 2: //-->   Tocamos Perfil  <--
                        perfilFragment framentProfile= new perfilFragment();
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
            RecyclerView recyclerView=bottomSheetView.findViewById(R.id.listaMateriaPuedeCursar);
            ArrayList<String> test=new ArrayList<>();
            test.add("INFO1");
            test.add("AM");
            test.add("AM");
            AdapterAgregaMateria adapterAgregaMateria=new AdapterAgregaMateria(PuedoCursar(materiasCursadas,programaAnal));
            recyclerView.setAdapter(adapterAgregaMateria);
            recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
            BottomSheetDialog sideSheetDialog=new BottomSheetDialog(MainActivity.this);
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
    principalFragment fragment= new principalFragment();
    getSupportFragmentManager().beginTransaction().add(R.id.fragmentPrincipalx,fragment).commit();

}
}
package com.example.miutn;

import android.content.Context;
import android.os.Bundle;

import com.example.miutn.network.api.ApiService;
import com.example.miutn.network.api.RetrofitClient;
import com.example.miutn.network.models.MateriasCursando;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.sidesheet.SideSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miutn.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

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
    private RecyclerView RecyclerMismaterias;
    private RecyclerView RecyclerMismateriasHoy;
    AdapterMisMaterias adapterMisMaterias=new AdapterMisMaterias();
    AdapterMisMaterias adapterMisMateriasHoy=new AdapterMisMaterias();
    FrameLayout sideSheetContainer;
    Retrofit retrofit = RetrofitClient.getClient();
    ExtendedFloatingActionButton extendedFloatingActionButton;
    ApiService apiService = retrofit.create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        linkElement();
        String profileTest="FEDE";
        Call<ArrayList<MateriasCursando>> call = apiService.obtenerMaterias(profileTest);
        call.enqueue(new Callback<ArrayList<MateriasCursando>>() {
            @Override
            public void onResponse(Call<ArrayList<MateriasCursando>> call, Response<ArrayList<MateriasCursando>> response) {
                if (response.isSuccessful()) {
                    ArrayList<MateriasCursando> materias = response.body();
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
        Call<ArrayList<MateriasCursando>> MateriasHoy = apiService.obtenerMateriasHoy(profileTest,Dia);
        MateriasHoy.enqueue(new Callback<ArrayList<MateriasCursando>>() {
            @Override
            public void onResponse(Call<ArrayList<MateriasCursando>> call, Response<ArrayList<MateriasCursando>> response) {
                if (response.isSuccessful()) {
                    ArrayList<MateriasCursando> materias = response.body();
                    ArrayList<String> nameMatery=new ArrayList<>();
                    if(materias.isEmpty()){
                        // TODO : Añadir Animacion indicando dia libre
                        Log.e("MIRA","ESTA VACIO HOY");
                        return;
                    }
                    for(MateriasCursando mater : materias){
                        nameMatery.add(mater.getMateria().getNombre());
                    }
                    adapterMisMateriasHoy.setData(materias);
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


    }



public void linkElement(){
    RecyclerMismaterias=findViewById(R.id.RecyclerMismaterias);
    RecyclerMismaterias.setAdapter(adapterMisMaterias);
    RecyclerMismaterias.setLayoutManager(new LinearLayoutManager(this));
    RecyclerMismateriasHoy=findViewById(R.id.RecyclerMismateriasHoy);
    RecyclerMismateriasHoy.setAdapter(adapterMisMateriasHoy);
    RecyclerMismateriasHoy.setLayoutManager(new LinearLayoutManager(this));
    sideSheetContainer=findViewById(R.id.sideSheetContainer);
    extendedFloatingActionButton=findViewById(R.id.fab);
//TODO Terminar esto que es para añadir mas materias
    //Tengo que  Mostrar todas las materias posibles horarios y sedes

  /*  extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //sideSheetContainer.setVisibility((sideSheetContainer.getVisibility()==View.VISIBLE)?View.GONE:View.VISIBLE);
            //Context context;

            BottomSheetDialog sideSheetDialog=new BottomSheetDialog(MainActivity.this);
            sideSheetDialog.setContentView(R.layout.tryshide);
            sideSheetDialog.show();
        }
    });/*/
}
}
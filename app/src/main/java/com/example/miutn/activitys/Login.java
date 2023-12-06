package com.example.miutn.activitys;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miutn.ControlDatos;
import com.example.miutn.R;
import com.example.miutn.databinding.ActivityLoginBinding;
import com.example.miutn.network.api.ApiService;
import com.example.miutn.network.api.RetrofitClient;
import com.example.miutn.network.models.NMateria;
import com.example.miutn.network.models.Perfil;
import com.example.miutn.security.CreacionHash;
import com.example.miutn.security.Credenciales;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {
    ImageView imageView;
//TextView loginRegistrarme,loginTengoCuenta,loginOlvideClave;
    Retrofit retrofit = RetrofitClient.getClient();
    Snackbar snackbar;
    ApiService apiService = retrofit.create(ApiService.class);
    private @NonNull ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_login);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        //View view1 = binding.getRoot();
        setContentView(binding.getRoot());
        //todo añadir bingin para el manejo de la vista
        //binding = Inflater.inflate(getLayoutInflater());
       // loginLinearRegistrar=findViewById(R.id.loginLinearRegistrar);   //-->   Se encarga del manejo de la vista del registro y toma de datos  <--
        //loginInicioSeccion=findViewById(R.id.loginInicioSeccion);       //-->   Se encarga del manejo de la vista del inicio de seccion         <--
     //   loginRegistrarme=findViewById(R.id.loginRegistrarme);
       // loginOlvideClave=findViewById(R.id.loginOlvideClave);
        binding.loginOlvideClave.setOnClickListener(v->{
            BottomSheetDialog sideSheetDialog = new BottomSheetDialog(v.getContext());
            LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.sidesheet_olvide_clave, null);
            TextInputLayout correo=view.findViewById(R.id.loginRestaurarClave);
            sideSheetDialog.setContentView(view);
            sideSheetDialog.show();
            Button button=view.findViewById(R.id.loginBtnRestaurarClave);
            button.setOnClickListener(v1->{
                if(correo.getEditText().getText().toString().isEmpty()){
                    correo.setError("Ingresa correo");
                    correo.setErrorEnabled(true);
                    return;
                }
                //todo deshabilito interfaz para no saturar servidor    <--
                button.setEnabled(false);
                apiService.restaurarClave(correo.getEditText().getText().toString()).enqueue(new Callback<Void>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Log.e("Transmicion","Correcta A : "+response.body());
                            TextView ss=snackbar.getView().findViewById(R.id.Pruebasnar);
                            ss.setText("Verifica tu correo");
                            snackbar.show();
                        }
                        else {
                            Log.e("Transmicion","Correcta B : ");
                            correo.setErrorEnabled(true);
                            correo.setError("Verifica");
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        TextView ss=snackbar.getView().findViewById(R.id.Pruebasnar);
                        ss.setText("ERS");
//                    snackbar.setText("Error con la conexion reintenta luego");
                        Log.e("Transmicion","Falla A : "+t.getMessage());
                    snackbar.show();
                    }
                });
            });
        });
       snackbar=Snackbar.make(getWindow().getDecorView(),"",Snackbar.LENGTH_LONG);
         Snackbar.SnackbarLayout snackbarLayout= (Snackbar.SnackbarLayout) snackbar.getView();
        @SuppressLint("InflateParams") View snackView =getWindow().getLayoutInflater().inflate(R.layout.snackbar_layout, null);
        snackbarLayout.addView(snackView, 0);
        snackbar.getView().setBackgroundColor(Color.parseColor("#80000000"));
        snackbar.show();
        binding.loginRegistrarme.setOnClickListener(v -> {
            binding.loginLinearRegistrar.setVisibility(View.VISIBLE);
            binding.loginInicioSeccion.setVisibility(View.GONE);
        });
        binding.loginTengoCuenta.setOnClickListener(v -> {
            //-->   Tengo que invertir y mostrar el otro formulario <--
            binding.loginLinearRegistrar.setVisibility(View.GONE);
            binding.loginInicioSeccion.setVisibility(View.VISIBLE);
        });
        //Button loginBotonIngresar=findViewById(R.id.loginBotonIngresar);
        //-->   Verificaciones boton ingresar   <--
        binding.loginBotonIngresar.setOnClickListener(v -> {
            //-->   Verificacion de formularios completos   <--

            //TextInputLayout loginUserPass=findViewById(R.id.loginUserPass);
            //TextInputLayout loginUserName=findViewById(R.id.loginUserName);
            if(binding.loginUserName.getEditText().getText().length()==0){
                binding.loginUserName.setError("Ingresa Usuario");
                return;
            }
            else{
                binding.loginUserName.setErrorEnabled(false);
            }
            if(binding.loginUserPass.getEditText().getText().length()==0){
                binding.loginUserPass.setError("Introduci clave");
                return;
            }
            else{
                binding.loginUserPass.setErrorEnabled(false);
            }

            //-->   TODO Enviar a servidor los datos    <--
            //-->   API server
            Credenciales credenciales=new Credenciales(String.valueOf(binding.loginUserName.getEditText().getText()),String.valueOf(binding.loginUserPass.getEditText().getText()));
            TextView ss=snackbar.getView().findViewById(R.id.Pruebasnar);
            ss.setText("Iniciando seccion \n aguarde");
            snackbar.show();
            //-->   TODO cambiar el snackbar por un progress bar    <--
            apiService.inicioSeccion(credenciales).enqueue(new Callback<Perfil>() {
                @Override
                public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                    if (response.isSuccessful()){
                        //-->   TODO Recordar el tema del sideSheet <--
                        Perfil perfil=response.body();

                        //-->   todo tengo problemas por la obtencion del programa analitico    <--
                        apiService.obtenerMateriasProgramaAnal(perfil.getCarrea()).enqueue(new Callback<ArrayList<NMateria>>() {
                            @Override
                            public void onResponse(Call<ArrayList<NMateria>> call, Response<ArrayList<NMateria>> response) {
                                if(response.isSuccessful()){
                                    ControlDatos.GuardarPerfil(getApplicationContext(),perfil);
                                    ArrayList<NMateria> programa=response.body();
                                    for(NMateria materia: programa){
                                        Log.e("OBTENCION PROGRAMA",materia.getName());
                                    }
                                    //todo Generar descarga del material podria utilizar task o algo para descargar el material

                                    ControlDatos.GuardarProgramaAnalitico(programa,getApplicationContext());
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    getApplicationContext().startActivity(intent);
                                    finish();
                                }
                                else{
                                    TextView ss=snackbar.getView().findViewById(R.id.Pruebasnar);
                                    ss.setText("Ups algo salio mal :( \n reintenta luego");
                                    snackbar.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ArrayList<NMateria>> call, Throwable t) {
                                TextView ss=snackbar.getView().findViewById(R.id.Pruebasnar);
                                ss.setText(""+t.getMessage());
                                snackbar.show();
                            }
                        });

                    }
                    else {
                        Log.e("MIRA","ERROR");
                        try {
                           // Log.e("ERROR",response.errorBody().string());
                            TextView ss=snackbar.getView().findViewById(R.id.Pruebasnar);
                            ss.setText(""+response.errorBody().string());
                            snackbar.show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Perfil> call, Throwable t) {
                    Log.e("Error inicio seccion",t.getMessage());
                    TextView ss=snackbar.getView().findViewById(R.id.Pruebasnar);
                    ss.setText(":/ Algo fallo en inicio de seccion");
                    snackbar.show();
                }
            });
        });
        Button loginRegister=findViewById(R.id.loginRegister);
        loginRegister.setOnClickListener(v->{
            if(!verificaCampos()){
                return;
            }
            Map<String,String> datos=new HashMap<>();

            String clave=CreacionHash.sha256(binding.loginUserPas.getEditText().getText().toString());
            datos.put("usuario",binding.loginUser.getEditText().getText().toString());
            datos.put("nombre",binding.loginUserName.getEditText().getText().toString());
            datos.put("legajo",binding.loginUserLegajo.getEditText().getText().toString());
            datos.put("correo",binding.loginCorreo.getEditText().getText().toString());
            datos.put("carrera",binding.loginCarrear.getEditText().getText().toString());
            apiService.existeUsuario(datos).enqueue(new Callback<ArrayList<NMateria>>() {
                @Override
                public void onResponse(Call<ArrayList<NMateria>> call, Response<ArrayList<NMateria>> response) {
                    if (response.isSuccessful()){
                        BottomSheetDialog sideSheetDialog = new BottomSheetDialog(v.getContext());
                        LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        @SuppressLint("InflateParams")  View view = inflater.inflate(R.layout.sidesheet_nuevo_usuario, null);
                        Button siguente=view.findViewById(R.id.sidesheetSiguente);
                        ChipGroup chipGroupLoginNuevoUsuario=view.findViewById(R.id.chipGroupLoginNuevoUsuario);
                        ArrayList<NMateria> materias=response.body();
                        for(NMateria materia : materias){
                            Chip chip=new Chip(v.getContext());
                            chip.setText(materia.getName());
                            chip.setCheckable(true);
                            chipGroupLoginNuevoUsuario.addView(chip);
                        }
                        sideSheetDialog.setContentView(view);
                        sideSheetDialog.show();
                        siguente.setOnClickListener(v->{
                            Map<String,String> datosRegistro=new HashMap<>();
                            datosRegistro.put("usuario",binding.loginUser.getEditText().getText().toString());
                            datosRegistro.put("correo",binding.loginCorreo.getEditText().getText().toString()+"@frba.utn.edu.ar");
                            datosRegistro.put("carrera",binding.loginCarrear.getEditText().getText().toString());
                            ArrayList<String> materiasSeleccionadas=new ArrayList<>();
                            for(Integer i:chipGroupLoginNuevoUsuario.getCheckedChipIds()){
                                Chip checkedChip = chipGroupLoginNuevoUsuario.findViewById(i);
                                if(checkedChip!=null){
                                    materiasSeleccionadas.add(String.valueOf(checkedChip.getText()));
                                }
                            }
                            datosRegistro.put("materiasSize", String.valueOf(materiasSeleccionadas.size()));
                            for(int i=0;i<materiasSeleccionadas.size();i++){
                                datosRegistro.put("materia"+i,materiasSeleccionadas.get(i));
                            }
                            datosRegistro.put("hash",clave);
                            datosRegistro.put("nombre",binding.loginUserName.getEditText().getText().toString());
                            datosRegistro.put("legajo",binding.loginUserLegajo.getEditText().getText().toString());
                            apiService.crearUsuario(datosRegistro).enqueue(new Callback<Perfil>() {
                                @Override
                                public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                                    if (response.isSuccessful()){
                                        Perfil perfil=response.body();
                                        ControlDatos.GuardarPerfil(getApplicationContext(),perfil);
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        getApplicationContext().startActivity(intent);
                                        finish();


                                    }
                                    else {
                                        Log.e("REGISTRO","ALGO FALLO");
                                    }
                                }

                                @Override
                                public void onFailure(Call<Perfil> call, Throwable t) {
                                Log.e("REGISTRO", t.getMessage());
                                }
                            });
                        });

                    }
                    else{
                        //-->   Usuario ya registrado   <--
                        TextView ss=snackbar.getView().findViewById(R.id.Pruebasnar);
                        ss.setText(":| Usuario ya registrado");
                        snackbar.show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<NMateria>> call, Throwable t) {
                    TextView ss=snackbar.getView().findViewById(R.id.Pruebasnar);
                    ss.setText(":/ Ups Error en conexion a internet");
                    snackbar.show();
                }
            });
        });
    }
    public boolean verificaCampos(){
        TextInputLayout loginUserName=findViewById(R.id.loginNombre);
        TextInputLayout loginCorreo=findViewById(R.id.loginCorreo);
        TextInputLayout loginUser=findViewById(R.id.loginUser);
        TextInputLayout loginUserPas=findViewById(R.id.loginUserPas);
        TextInputLayout loginCarrear=findViewById(R.id.loginCarrear);
        TextInputLayout loginUserLegajo=findViewById(R.id.loginUserLegajo);
        if(binding.loginUserName.getEditText().getText().length()==0){
            binding.loginUserName.setError("Ingresa nombre");
            return false;
        }
        else{
            binding.loginUserName.setErrorEnabled(false);
        }
        if(binding.loginCorreo.getEditText().getText().length()==0){
            //-->   Yo añado el @frba.utn.edu.ar
            binding.loginCorreo.setError("Verifica correo");
            return false;
        }
        else{
            binding.loginCorreo.setErrorEnabled(false);
        }
        if(binding.loginUser.getEditText().getText().length()==0 ){
            binding.loginUser.setError("Verifica usuario");
            return false;
        }
        else{
            binding.loginUser.setErrorEnabled(false);
        }

        if(binding.loginCarrear.getEditText().getText().length()==0 ){
            binding.loginCarrear.setError("Introduce tu carrera");
            return false;
        }
        else{
            binding.loginCarrear.setErrorEnabled(false);
        }

        if(binding.loginUserPas.getEditText().getText().length()==0 ){
            binding.loginCarrear.setError("Introduce clave");
            return false;
        }
        else{
            binding.loginUserPas.setErrorEnabled(false);
        }
        if(binding.loginUserLegajo.getEditText().getText().length()==0 ){
            binding.loginCarrear.setError("Introduce legajo");
            return false;
        }
        else{
            binding.loginUserLegajo.setErrorEnabled(false);
        }

        return true;

    }

    public void click(View view) {
    }
}
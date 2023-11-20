package com.example.miutn;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {
LinearLayout loginLinearRegistrar,loginInicioSeccion;
//Button loginRegister;
TextView loginRegistrarme,loginTengoCuenta;
    Retrofit retrofit = RetrofitClient.getClient();
    Snackbar snackbar;
    ApiService apiService = retrofit.create(ApiService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginLinearRegistrar=findViewById(R.id.loginLinearRegistrar);   //-->   Se encarga del manejo de la vista del registro y toma de datos  <--
        loginInicioSeccion=findViewById(R.id.loginInicioSeccion);       //-->   Se encarga del manejo de la vista del inicio de seccion         <--
        loginRegistrarme=findViewById(R.id.loginRegistrarme);
        //-->   TODO personalizar snackbar  <--
        snackbar=Snackbar.make(getWindow().getDecorView(),"",Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(Color.parseColor("#80000000"));
        View snackbarView = snackbar.getView();

        // Obtener el TextView dentro del Snackbar
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);

        // Configurar el TextView para centrar el texto
        textView.setGravity(Gravity.CENTER);

        loginRegistrarme.setOnClickListener(v -> {
            loginLinearRegistrar.setVisibility(View.VISIBLE);
            loginInicioSeccion.setVisibility(View.GONE);
        });
        loginTengoCuenta=findViewById(R.id.loginTengoCuenta);
        loginTengoCuenta.setOnClickListener(v -> {
            //-->   Tengo que invertir y mostrar el otro formulario <--
            loginLinearRegistrar.setVisibility(View.GONE);
            loginInicioSeccion.setVisibility(View.VISIBLE);
        });
        Button loginBotonIngresar=findViewById(R.id.loginBotonIngresar);
        //-->   Verificaciones boton ingresar   <--
        loginBotonIngresar.setOnClickListener(v -> {
            //-->   Verificacion de formularios completos   <--
            TextInputLayout loginUserPass=findViewById(R.id.loginUserPass);
            TextInputLayout loginUserName=findViewById(R.id.loginUserName);
            if(loginUserName.getEditText().getText().length()==0){
                loginUserName.setError("Ingresa Usuario");
                return;
            }
            else{
            loginUserName.setErrorEnabled(false);
            }
            if(loginUserPass.getEditText().getText().length()==0){
                loginUserPass.setError("Introduci clave");
                return;
            }
            else{
                loginUserPass.setErrorEnabled(false);
            }

            //-->   TODO Enviar a servidor los datos    <--
            //-->   API server
            Credenciales credenciales=new Credenciales(String.valueOf(loginUserName.getEditText().getText()),String.valueOf(loginUserPass.getEditText().getText()));
            snackbar.setText("Iniciando seccion \n aguarde");
            snackbar.show();
            //-->   TODO cambiar el snackbar por un progress bar    <--
            apiService.inicioSeccion(credenciales).enqueue(new Callback<Perfil>() {
                @Override
                public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                    if (response.isSuccessful()){
                        //-->   TODO Recordar el tema del sideSheet <--
                        Perfil perfil=response.body();
                        ControlDatos.GuardarPerfil(getApplicationContext(),perfil);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        getApplicationContext().startActivity(intent);
                        finish();
                    }
                    else {
                        Log.e("MIRA","ERROR");
                        try {
                           // Log.e("ERROR",response.errorBody().string());
                            snackbar.setText(""+response.errorBody().string());
                            snackbar.show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Perfil> call, Throwable t) {
                    //TODO Mostrar SnackBar
                    Log.e("Error inicio seccion",t.getMessage());
                    snackbar.setText("Error en inicio de seccion verifica datos");
                    snackbar.show();
                }
            });
          //  Log.e("MIRA calculo de HASH", CreacionHash.sha256(String.valueOf(loginUserPass.getEditText().getText())));
        });

        //TODO Registro de usuario  <--
        Button loginRegister=findViewById(R.id.loginRegister);
        loginRegister.setOnClickListener(v->{
            //TODO verificar campos completos
            if(!verificaCampos()){
                return;
            }
            //TODO enviar datos al servidor buscando que no este registrado <--
            Map<String,String> datos=new HashMap<>();
            TextInputLayout loginUser=findViewById(R.id.loginUser);
            TextInputLayout loginCorreo=findViewById(R.id.loginCorreo);
            TextInputLayout loginCarrear=findViewById(R.id.loginCarrear);
            datos.put("usuario",loginUser.getEditText().getText().toString());
            datos.put("correo",loginCorreo.getEditText().getText().toString());
            datos.put("carrera",loginCarrear.getEditText().getText().toString());
            apiService.existeUsuario(datos).enqueue(new Callback<ArrayList<NMateria>>() {
                @Override
                public void onResponse(Call<ArrayList<NMateria>> call, Response<ArrayList<NMateria>> response) {
                    if (response.isSuccessful()){
                        //-->   todo Mostrar sideSheet con la informacion   <--
                        BottomSheetDialog sideSheetDialog = new BottomSheetDialog(v.getContext());
                        LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = inflater.inflate(R.layout.sidesheet_nuevo_usuario, null);
                        Button siguente=view.findViewById(R.id.sidesheetSiguente);
                        ChipGroup chipGroupLoginNuevoUsuario=view.findViewById(R.id.chipGroupLoginNuevoUsuario);
                        ArrayList<NMateria> materias=response.body();
                        for(NMateria materia : materias){
                            Log.e("Mat",materia.getName());
                            Chip chip=new Chip(v.getContext());
                            chip.setText(materia.getName());
                            chip.setCheckable(true);
                            chipGroupLoginNuevoUsuario.addView(chip);

                        }
                        sideSheetDialog.setContentView(view);
                        sideSheetDialog.show();
                        siguente.setOnClickListener(v->{
                            //TODO envio toda la informacion a servidor y caso satifactorio registro    <--
                            Log.e("MIRA","ENVIANDO SERVIRO");
                            Map<String,String> datosRegistro=new HashMap<>();
                            datosRegistro.put("usuario",loginUser.getEditText().getText().toString());
                            datosRegistro.put("correo",loginCorreo.getEditText().getText().toString());
                            datosRegistro.put("carrera",loginCarrear.getEditText().getText().toString());
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
                            datosRegistro.put("hash","5555");
                            datosRegistro.put("nombre","5555");
                            datosRegistro.put("legajo","5555");
                            apiService.crearUsuario(datosRegistro).enqueue(new Callback<Perfil>() {
                                @Override
                                public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                                    if (response.isSuccessful()){
                                        Log.e("REGISTRO","COMPLETO");
                                        //Todo registro finalizado con exito    <--
                                        //todo falta añadir correo institucional - nombre usuario
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
                                Log.e("REGISTRO",t.getMessage().toString());
                                }
                            });
                        });

                    }
                    else{
                        Log.e("Existencia","ERROR RARO");
                        //-->   Usuario ya registrado   <--
                        snackbar.setText("Este usuario ya se encuentra registrado");
                        snackbar.show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<NMateria>> call, Throwable t) {
                snackbar.setText("Error en conexion a internet verificar");
                snackbar.show();
                }
            });
        });
    }
    public boolean verificaCampos(){
        TextInputLayout loginUserName=findViewById(R.id.loginNombre);
        TextInputLayout loginCorreo=findViewById(R.id.loginCorreo);
        TextInputLayout loginUser=findViewById(R.id.loginUser);
        TextInputLayout loginCarrear=findViewById(R.id.loginCarrear);
       // TextInputLayout loginUserPass=findViewById(R.id.loginUserPass);
        if(loginUserName.getEditText().getText().length()==0){
            loginUserName.setError("Ingresa nombre");
            return false;
        }
        else{
            loginUserName.setErrorEnabled(false);
        }
        if(loginCorreo.getEditText().getText().length()==0 || !loginCorreo.getEditText().getText().toString().contains("@")){
            loginCorreo.setError("Verifica correo");
            return false;
        }
        else{
            loginCorreo.setErrorEnabled(false);
        }
        if(loginUser.getEditText().getText().length()==0 ){
            loginUser.setError("Verifica usuario");
            return false;
        }
        else{
            loginUser.setErrorEnabled(false);
        }

        if(loginCarrear.getEditText().getText().length()==0 ){
            loginCarrear.setError("Introduce tu carrera");
            return false;
        }
        else{
            loginCarrear.setErrorEnabled(false);
        }
        return true;

    }
}
package com.example.miutn;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miutn.network.api.ApiService;
import com.example.miutn.network.api.RetrofitClient;
import com.example.miutn.network.models.Perfil;
import com.example.miutn.security.CreacionHash;
import com.example.miutn.security.Credenciales;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {
LinearLayout loginLinearRegistrar,loginInicioSeccion;
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

        loginRegistrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLinearRegistrar.setVisibility(View.VISIBLE);
                loginInicioSeccion.setVisibility(View.GONE);
            }
        });
        loginTengoCuenta=findViewById(R.id.loginTengoCuenta);
        loginTengoCuenta.setOnClickListener(v -> {
            //-->   Tengo que invertir y mostrar el otro formulario <--
            loginLinearRegistrar.setVisibility(View.GONE);
            loginInicioSeccion.setVisibility(View.VISIBLE);
        });
        Button loginBotonIngresar=findViewById(R.id.loginBotonIngresar);
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
            apiService.inicioSeccion(credenciales).enqueue(new Callback<Perfil>() {
                @Override
                public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                    //TODO Verificar si son correctos los datos - Guardar perfil - Llamar a mainActivity    <--
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
    }
}
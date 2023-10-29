package com.example.miutn;

import android.util.Log;

import com.example.miutn.network.api.ApiService;
import com.example.miutn.network.api.RetrofitClient;
import com.example.miutn.network.models.Perfil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControladorCargaPerfil {
    ApiService apiService;
     ControladorCargaPerfil(){
    }
     ControladorCargaPerfil(ApiService apiService1){
        apiService=apiService1;
    }
    public boolean cargarPerfil(Perfil perfil){
        final boolean[] salida = {true};
        perfil.setId("NUEVO");
        apiService.subirPerfil(perfil).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                salida[0] =response.isSuccessful();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("ERROR SUBIDA",t.getMessage());
            }
        });
        return salida[0];
    }
    public Perfil descargaPerfil(){
        Perfil perfil=new Perfil();
         return perfil;
    }
}

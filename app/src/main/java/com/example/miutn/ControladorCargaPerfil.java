package com.example.miutn;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.miutn.network.api.ApiService;
import com.example.miutn.network.models.Perfil;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** @noinspection unused, unused, unused, unused */
public class ControladorCargaPerfil {
    ApiService apiService;

    ControladorCargaPerfil() {
    }

    ControladorCargaPerfil(ApiService apiService1) {
        apiService = apiService1;
    }

    public boolean cargarPerfil(Perfil perfil) {
        final boolean[] salida = {true};
        perfil.setId("NUEVO");
        apiService.subirPerfil(perfil).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                salida[0] = response.isSuccessful();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("ERROR SUBIDA", Objects.requireNonNull(t.getMessage()));
            }
        });
        return salida[0];
    }

    public Perfil descargaPerfil() {
        Perfil perfil = new Perfil();
        return perfil;
    }
}

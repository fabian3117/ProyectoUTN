package com.example.miutn;

import android.os.AsyncTask;

import com.example.miutn.enums.Carreras;
import com.example.miutn.network.api.ApiService;
import com.example.miutn.network.api.RetrofitClient;

import java.io.IOException;

import retrofit2.Retrofit;

/*
Aca vamos a manejar las consultas de API atraves de AsyncTask
 */
public class Hilo<T> extends AsyncTask<Void,Void, T> {
    Retrofit retrofit = RetrofitClient.getClient();
    ApiService apiService = retrofit.create(ApiService.class);
    @Override
    protected T doInBackground(Void... voids) {
        T resultado;
        try {
            resultado= (T) apiService.obtenerMateriasProgramaAnal(Carreras.Electronica.getValorAsociado()).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    @Override
    protected void onPostExecute(T resultado) {

    }
}

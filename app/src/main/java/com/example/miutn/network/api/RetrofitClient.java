package com.example.miutn.network.api;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Setting of retrofitClient
 */
public class RetrofitClient {
    private static final String urlBase="http://192.168.0.131:8080";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)  // Establecer el tiempo de espera de conexión
                .readTimeout(30, TimeUnit.SECONDS)     // Establecer el tiempo de espera de lectura
                .writeTimeout(30, TimeUnit.SECONDS)    // Establecer el tiempo de espera de escritura
                .protocols(Arrays.asList(Protocol.HTTP_1_1)) // Establecer protocolos de red
                .build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(urlBase)
                    .addConverterFactory(GsonConverterFactory.create())
                    //.client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

}

package com.example.miutn.network.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import com.example.miutn.network.models.*;

/**
 * Configuration of end-Point to Retrofit using
 */
public interface ApiService {
    @GET("obtenerMaterias/{profile}")
    Call<ArrayList<MateriasCursando>> obtenerMaterias(@Path("profile") String profile);
    @GET("obtenerMaterias/{profile}/{dia}")
    Call<ArrayList<MateriasCursando>> obtenerMateriasHoy(@Path("profile") String profile,@Path("dia") String dia);
}

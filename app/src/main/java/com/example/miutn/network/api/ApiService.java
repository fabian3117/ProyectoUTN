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
    //-->   Obtengo materias CURSANDO   <--
    @GET("obtenerMaterias/{profile}")
    Call<ArrayList<MateriasCursando>> obtenerMaterias(@Path("profile") String profile);
    @GET("obtenerMaterias/{profile}/{dia}")
    Call<ArrayList<MateriasCursando>> obtenerMateriasHoy(@Path("profile") String profile,@Path("dia") String dia);
    //-->   Obtencion de programa analitico <--
    @GET("obtenerPrograma/{carreras}")
    Call<ArrayList<Materia>> obtenerMateriasProgramaAnal(@Path("carreras") String carreras);

    //-->   Obtener materias CURSADAS
    @GET("obtenerMateriasCursadas/{profile}")
    Call<ArrayList<Materia>> obtenerMateriasCursadas(@Path("profile") String profile);
    //-->   Materias que puedo cursar   <--
     @GET("puedeCursar/{profile}")
    Call<ArrayList<String>> obtenerPuedeCursar(@Path("profile") String profile);
}

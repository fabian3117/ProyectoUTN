package com.example.miutn.network.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import com.example.miutn.enums.Carreras;
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
    @PUT("obtenerPrograma")
    Call<ArrayList<NMateria>> obtenerMateriasProgramaAnal(@Body Carreras carrera);

    //-->   Obtener materias CURSADAS
    @GET("obtenerMateriasCursadas/{profile}")
    Call<ArrayList<Materia>> obtenerMateriasCursadas(@Path("profile") String profile);
    //-->   Materias que puedo cursar   <--
     @GET("puedeCursar/{profile}")
    Call<ArrayList<String>> obtenerPuedeCursar(@Path("profile") String profile);
     @POST("guardarMateria/{profile}")
    Call<NMateriasCursando> guardarNuevaMateria(@Path("profile")String profile,@Body NMateriasCursando nuevaMateria);    //-->   Subi nueva materia al servidor  <--
    @GET("puedoCursar")
    Call<ArrayList<NMateria>> materiasPuedoCursar();    //-->   Obtener materias que puedo cursar   <--
    @POST("CargaPerfil")
    Call<Void> subirPerfil(@Body Perfil perfil);
    @GET("descargarPerfil/{perfilId}")
    Call<Perfil> descargaPerfil(@Path("perfilId") String perfilId);

}

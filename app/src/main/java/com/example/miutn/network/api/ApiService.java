package com.example.miutn.network.api;

import com.example.miutn.enums.Carreras;
import com.example.miutn.network.models.FechasExamenes;
import com.example.miutn.network.models.Materia;
import com.example.miutn.network.models.MateriasCursando;
import com.example.miutn.network.models.NMateria;
import com.example.miutn.network.models.NMateriasCursando;
import com.example.miutn.network.models.Perfil;
import com.example.miutn.network.models.Temario;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Configuration of end-Point to Retrofit using
 * @noinspection unused
 */
public interface ApiService {
    //-->   Obtengo materias CURSANDO   <--
    @GET("obtenerMaterias/{profile}")
    Call<ArrayList<MateriasCursando>> obtenerMaterias(@Path("profile") String profile);

    @GET("obtenerMaterias/{profile}/{dia}")
    Call<ArrayList<MateriasCursando>> obtenerMateriasHoy(@Path("profile") String profile, @Path("dia") String dia);

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
    Call<NMateriasCursando> guardarNuevaMateria(@Path("profile") String profile, @Body NMateriasCursando nuevaMateria);    //-->   Subi nueva materia al servidor  <--

    @GET("puedoCursar")
    Call<ArrayList<NMateria>> materiasPuedoCursar();    //-->   Obtener materias que puedo cursar   <--

    @POST("CargaPerfil")
    Call<Void> subirPerfil(@Body Perfil perfil);

    @GET("descargarPerfil/{perfilId}")
    Call<Perfil> descargaPerfil(@Path("perfilId") String perfilId);

    @GET("obtenerFechas")
    Call<ArrayList<FechasExamenes>> obtenerProximasFechas();
    //-->   Obtener apuntesRecomendados hoy <--
    @GET("obtenerApuntes/{profile}")
    Call<ArrayList<Temario>> obtenerApuntes(@Path("profile") String profile);

    @GET("controladorPrincipal/materiasT/{carrera}")
    Call<ArrayList<NMateria>> materiasAsociadas(@Path("carrera") String carrera);
}

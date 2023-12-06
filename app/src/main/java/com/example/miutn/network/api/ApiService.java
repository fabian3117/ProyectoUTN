package com.example.miutn.network.api;

import androidx.annotation.NonNull;

import com.example.miutn.enums.Carreras;
import com.example.miutn.network.models.FechasExamenes;
import com.example.miutn.network.models.Materia;
import com.example.miutn.network.models.MateriasCursando;
import com.example.miutn.network.models.NMateria;
import com.example.miutn.network.models.NMateriasCursando;
import com.example.miutn.network.models.Perfil;
import com.example.miutn.network.models.Temario;
import com.example.miutn.security.Credenciales;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

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

    //-->   Obtencion de programa analitico <--
    @POST("obtenerPrograma")
    Call<ArrayList<NMateria>> obtenerMateriasProgramaAnal(@Body Carreras carrera);



    @POST("guardarMateria/{profile}")
    Call<NMateriasCursando> guardarNuevaMateria(@Path("profile") String profile, @Body NMateriasCursando nuevaMateria);    //-->   Subi nueva materia al servidor  <--


    @POST("CargaPerfil")
    Call<Void> subirPerfil(@Body Perfil perfil);


    @GET("obtenerFechas")
    Call<ArrayList<FechasExamenes>> obtenerProximasFechas();
    //-->   Obtener apuntesRecomendados hoy <--
    @GET("obtenerApuntes/{profile}")
    Call<ArrayList<Temario>> obtenerApuntes(@Path("profile") String profile);

    @GET("controladorPrincipal/materiasT/{carrera}")
    Call<ArrayList<NMateria>> materiasAsociadas(@Path("carrera") String carrera);

    //-->   End-Point seccion   <--

    /**
     * Inicia seccion enviando las credenciales
     * @param credenciales
     * @return la respuesta en caso de inicio de seccion correcto es mi perfil
     */
    @POST("seccionInicio")
    Call<Perfil> inicioSeccion(@Body Credenciales credenciales); //-->   TODO VERIFICAR QUE ESTE CALCULANDO HASH Y ENVIANDO ESO  <--
    @POST("verificaUsuarioExistente")
    Call<ArrayList<NMateria>> existeUsuario(@Body Map<String,String> datos);    //-->   TODO Envio Par NombreUsuario-Correo <--

    @POST("nuevoUsuario")
    Call<Perfil> crearUsuario(@Body Map<String,String> datos);
    @POST("restaurarClave")
    Call<Void> restaurarClave(@Body String correo);
    //-->   Voy a pedir un apunte particular ejemplo Fisica1OpticaGeometricaTema1

    /**
     * Solicito un archivo particular para poder verlo en markdown
     * @param apunteID  Identificador del apunte
     * @return String con el contenido del archivo
     */
    @GET("apuntes/{apunteID}")
    Call<String> solicitaApunte(@Path("apunteID") @NonNull String apunteID);
    @POST("temaVisto/{temaID}")
    Call<Void> temaVisto(@Path("temaID") @NonNull String temaID, @Body Perfil profile);

}

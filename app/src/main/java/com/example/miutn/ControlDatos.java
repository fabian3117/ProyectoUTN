package com.example.miutn;
//-->   Este objeto tiene que encargarse de manejar los datos que estan en local

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.example.miutn.network.models.FechasExamenes;
import com.example.miutn.network.models.Materia;
import com.example.miutn.network.models.MateriasCursando;
import com.example.miutn.network.models.Profile;
import com.example.miutn.network.models.Temario;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ControlDatos {
    private final static  String sharedPreferenceKeyObtenerMateriasCursadas="ObtenerMateriasCursadas";
    private final static  String sharedPreferenceKeyObtenerProgramaAnalitico="programaAnalitico";
    private final static String sharedPreferenceNameContainer="MiPreferencia";
    private final static String sharedPreferenceKeyTemarioRecomendado="listaKey";
    private final static String sharedPreferenceKeyFechasExamenes="KeyFechasExam";
    private final static String sharedPreferenceKeyObtenerMateriasCursando="KeyMateriasCursando";
    private final static String sharedPreferenceKeyObtenerProfile="KeyProfile";
//-->   Tengo que tener encuenta las excepciones    <--
    public static ArrayList<Temario> ObtenerRecomendaciones(Context contexto){
        ArrayList<Temario> salida=new ArrayList<>();

        SharedPreferences sharedPreferences = contexto.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        String listaSerializada  = sharedPreferences.getString(sharedPreferenceKeyTemarioRecomendado, null);
        if (listaSerializada != null) {
            try {
                byte[] listaBytes = Base64.decode(listaSerializada, Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(listaBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                ArrayList<Temario> listaObjetos = (ArrayList<Temario>) objectInputStream.readObject();
                for(Temario objeto:listaObjetos){
                    Log.e("MIRA",objeto.getApunte());
                }
                salida=listaObjetos;
                // Ahora tienes tu ArrayList deserializado en "listaObjetos"
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return salida;
    }
    public static boolean ActualizarRecomendaciones(Context context,ArrayList<Temario> temarios){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(sharedPreferenceKeyTemarioRecomendado);
        editor.apply();
        if (sharedPreferences.contains(sharedPreferenceKeyTemarioRecomendado)) {
            //-->   Si sigue en el contenedor es por que no se elimino correctamente    <--

            return false;
        }
        return GuardarRecomendaciones(context,temarios);
    }
    public static boolean GuardarRecomendaciones(Context context,ArrayList<Temario> temarios){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(temarios);

            String listaSerializadaGuardar = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(sharedPreferenceKeyTemarioRecomendado, listaSerializadaGuardar);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //-->   True : Se guardo correctamente -- False : No se guardo  <--
        return (sharedPreferences.contains(sharedPreferenceKeyTemarioRecomendado));

    }
    public static boolean ExisteRecomendaciones(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);

        return (sharedPreferences.contains(sharedPreferenceKeyTemarioRecomendado));

    }
    public static ArrayList<Materia> ObtenerMateriasCursadas(Context context){
        ArrayList<Materia> materias=new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        String listaSerializada  = sharedPreferences.getString(sharedPreferenceKeyObtenerMateriasCursadas, null);
        if(listaSerializada!=null){
            try {
                byte[] listaBytes = Base64.decode(listaSerializada, Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(listaBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                ArrayList<Materia> listaObjetos = (ArrayList<Materia>) objectInputStream.readObject();
                materias=listaObjetos;
                // Ahora tienes tu ArrayList deserializado en "listaObjetos"
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return materias;
    }
    public static ArrayList<Materia> ObtencionProgramaAnal(Context context){
        ArrayList<Materia> materias=new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        String listaSerializada  = sharedPreferences.getString(sharedPreferenceKeyObtenerProgramaAnalitico, null);
        if(listaSerializada!=null){
            try {
                byte[] listaBytes = Base64.decode(listaSerializada, Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(listaBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                ArrayList<Materia> listaObjetos = (ArrayList<Materia>) objectInputStream.readObject();
                materias=listaObjetos;
                // Ahora tienes tu ArrayList deserializado en "listaObjetos"
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return materias;
    }
    public static boolean VerificaDatos(Context context){
        //-->   Ve
        return false;
    }
    public static ArrayList<FechasExamenes> ObtencionFechasExamenes(Context context){
        ArrayList<FechasExamenes> salida=new ArrayList<>();

        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        String listaSerializada  = sharedPreferences.getString(sharedPreferenceKeyFechasExamenes, null);
        if (listaSerializada != null) {
            try {
                byte[] listaBytes = Base64.decode(listaSerializada, Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(listaBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                ArrayList<FechasExamenes> listaObjetos = (ArrayList<FechasExamenes>) objectInputStream.readObject();
                salida=listaObjetos;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return salida;

    }
    public static boolean GuardarFechasExamenes(Context context,ArrayList<FechasExamenes> Fechas){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(Fechas);

            String listaSerializadaGuardar = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(sharedPreferenceKeyFechasExamenes, listaSerializadaGuardar);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //-->   True : Se guardo correctamente -- False : No se guardo  <--
        return (sharedPreferences.contains(sharedPreferenceKeyFechasExamenes));
    }
    public static ArrayList<MateriasCursando> ObtencionObtenerMateriasCursando(Context context){
        ArrayList<MateriasCursando> salida=new ArrayList<>();

        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        String listaSerializada  = sharedPreferences.getString(sharedPreferenceKeyObtenerMateriasCursando, null);
        if (listaSerializada != null) {
            try {
                byte[] listaBytes = Base64.decode(listaSerializada, Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(listaBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                ArrayList<MateriasCursando> listaObjetos = (ArrayList<MateriasCursando>) objectInputStream.readObject();
                salida=listaObjetos;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return salida;
    }
    public static boolean GuardarProfile(Context context,Profile perfil){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(perfil);
            String listaSerializadaGuardar = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(sharedPreferenceKeyObtenerProfile, listaSerializadaGuardar);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error",e.getMessage());
        }
        //-->   True : Se guardo correctamente -- False : No se guardo  <--
        return (sharedPreferences.contains(sharedPreferenceKeyObtenerProfile));
    }
public static Profile ObtencionPerfil(Context context){
        Profile salida=new Profile();
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        String listaSerializada  = sharedPreferences.getString(sharedPreferenceKeyObtenerProfile, null);
        if (listaSerializada != null) {
            try {
                byte[] listaBytes = Base64.decode(listaSerializada, Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(listaBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Profile listaObjeto = (Profile) objectInputStream.readObject();
                salida=listaObjeto;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return salida;
    }
    public static boolean GuardarMateriasCursando(Context context,ArrayList<MateriasCursando> MateriasCurse){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(MateriasCurse);
            String listaSerializadaGuardar = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(sharedPreferenceKeyObtenerMateriasCursando, listaSerializadaGuardar);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error",e.getMessage());
        }
        //-->   True : Se guardo correctamente -- False : No se guardo  <--
        return (sharedPreferences.contains(sharedPreferenceKeyObtenerMateriasCursando));

}
}
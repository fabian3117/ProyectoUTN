package com.example.miutn;
//-->   Este objeto tiene que encargarse de manejar los datos que estan en local

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.example.miutn.network.models.FechasExamenes;
import com.example.miutn.network.models.Materia;
import com.example.miutn.network.models.NMateria;
import com.example.miutn.network.models.NMateriasCursando;
import com.example.miutn.network.models.Perfil;
import com.example.miutn.network.models.Profile;
import com.example.miutn.network.models.Temario;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;

/** @noinspection ALL, unused, unused, unused, unused, unused, unused, unused */
public class ControlDatos {
    private final static String sharedPreferenceKeyObtenerMateriasCursadas = "ObtenerMateriasCursadas";
    private final static String sharedPreferenceKeyObtenerProgramaAnalitico = "programaAnalitico";
    private final static String sharedPreferenceNameContainer = "MiPreferencia";
    private final static String sharedPreferenceKeyObtenerProgramaAnal = "programaAnaliticoNuevaVersion";
    private final static String sharedPreferenceKeyObtenerPerfil = "PERFIL";
    private final static String sharedPreferenceKeyTemarioRecomendado = "listaKey";
    private final static String sharedPreferenceKeyFechasExamenes = "KeyFechasExam";
    private final static String sharedPreferenceKeyObtenerMateriasCursando = "KeyMateriasCursando";
    private final static String sharedPreferenceKeyObtenerProfile = "KeyProfile";

    //-->   Tengo que tener encuenta las excepciones    <--
    public static ArrayList<Temario> ObtenerRecomendaciones(Context contexto) {
        ArrayList<Temario> salida = new ArrayList<>();

        SharedPreferences sharedPreferences = contexto.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        String listaSerializada = sharedPreferences.getString(sharedPreferenceKeyTemarioRecomendado, null);
        if (listaSerializada != null) {
            try {
                byte[] listaBytes = Base64.decode(listaSerializada, Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(listaBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                ArrayList<Temario> listaObjetos = (ArrayList<Temario>) objectInputStream.readObject();
                for (Temario objeto : listaObjetos) {
                    Log.e("MIRA", objeto.getApunte());
                }
                salida = listaObjetos;
                // Ahora tienes tu ArrayList deserializado en "listaObjetos"
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return salida;
    }

    public static boolean ActualizarRecomendaciones(Context context, ArrayList<Temario> temarios) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(sharedPreferenceKeyTemarioRecomendado);
        editor.apply();
        if (sharedPreferences.contains(sharedPreferenceKeyTemarioRecomendado)) {
            //-->   Si sigue en el contenedor es por que no se elimino correctamente    <--

            return false;
        }
        return GuardarRecomendaciones(context, temarios);
    }

    public static boolean GuardarRecomendaciones(Context context, ArrayList<Temario> temarios) {
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

    public static boolean ExisteRecomendaciones(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);

        return (sharedPreferences.contains(sharedPreferenceKeyTemarioRecomendado));

    }

    public static ArrayList<Materia> ObtenerMateriasCursadas(Context context) {
        ArrayList<Materia> materias = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        String listaSerializada = sharedPreferences.getString(sharedPreferenceKeyObtenerMateriasCursadas, null);
        if (listaSerializada != null) {
            try {
                byte[] listaBytes = Base64.decode(listaSerializada, Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(listaBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                ArrayList<Materia> listaObjetos = (ArrayList<Materia>) objectInputStream.readObject();
                materias = listaObjetos;
                // Ahora tienes tu ArrayList deserializado en "listaObjetos"
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return materias;
    }

    public static ArrayList<Materia> ObtencionProgramaAnal(Context context) {
        ArrayList<Materia> materias = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        String listaSerializada = sharedPreferences.getString(sharedPreferenceKeyObtenerProgramaAnalitico, null);
        if (listaSerializada != null) {
            try {
                byte[] listaBytes = Base64.decode(listaSerializada, Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(listaBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                ArrayList<Materia> listaObjetos = (ArrayList<Materia>) objectInputStream.readObject();
                materias = listaObjetos;
                // Ahora tienes tu ArrayList deserializado en "listaObjetos"
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return materias;
    }

    public static boolean VerificaDatos(Context context) {
        //-->   Ve
        return false;
    }

    public static ArrayList<FechasExamenes> ObtencionFechasExamenes(Context context) {
        ArrayList<FechasExamenes> salida = new ArrayList<>();

        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        String listaSerializada = sharedPreferences.getString(sharedPreferenceKeyFechasExamenes, null);
        if (listaSerializada != null) {
            try {
                byte[] listaBytes = Base64.decode(listaSerializada, Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(listaBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                ArrayList<FechasExamenes> listaObjetos = (ArrayList<FechasExamenes>) objectInputStream.readObject();
                salida = listaObjetos;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return salida;

    }

    public static void GuardarFechasExamenes(Context context, ArrayList<FechasExamenes> Fechas) {
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
        sharedPreferences.contains(sharedPreferenceKeyFechasExamenes);
    }

    public static ArrayList<NMateriasCursando> ObtencionObtenerMateriasCursando(Context context) {
        ArrayList<NMateriasCursando> salida = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        String listaSerializada = sharedPreferences.getString(sharedPreferenceKeyObtenerMateriasCursando, null);
        if (listaSerializada != null) {
            try {
                byte[] listaBytes = Base64.decode(listaSerializada, Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(listaBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                ArrayList<NMateriasCursando> listaObjetos = (ArrayList<NMateriasCursando>) objectInputStream.readObject();
                salida = listaObjetos;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return salida;
    }

    public static void GuardarProfile(Context context, Profile perfil) {
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
            Log.e("error", Objects.requireNonNull(e.getMessage()));
        }
        //-->   True : Se guardo correctamente -- False : No se guardo  <--
        sharedPreferences.contains(sharedPreferenceKeyObtenerProfile);
    }

    public static Profile ObtencionPerfil(Context context) {
        Profile salida = new Profile();
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        String listaSerializada = sharedPreferences.getString(sharedPreferenceKeyObtenerProfile, null);
        if (listaSerializada != null) {
            try {
                byte[] listaBytes = Base64.decode(listaSerializada, Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(listaBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Profile listaObjeto = (Profile) objectInputStream.readObject();
                salida = listaObjeto;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return salida;
    }

    public static void GuardarMateriasCursando(Context context, ArrayList<NMateriasCursando> MateriasCurse) {
        //todo tengo que guardar dentro de perfil.materiaCursando
        Perfil perfil=ObtenerPerfil(context);
        perfil.setMateriasCursando(MateriasCurse);
        GuardarPerfil(context,perfil);
        /*
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
            Log.e("error", Objects.requireNonNull(e.getMessage()));
        }
        //-->   True : Se guardo correctamente -- False : No se guardo  <--
        sharedPreferences.contains(sharedPreferenceKeyObtenerMateriasCursando);*/

    }

    public static boolean GuardarPerfil(Context context, Perfil perfil) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(perfil);
            String listaSerializadaGuardar = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(sharedPreferenceKeyObtenerPerfil, listaSerializadaGuardar);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error", Objects.requireNonNull(e.getMessage()));
        }
        //-->   True : Se guardo correctamente -- False : No se guardo  <--
        return (sharedPreferences.contains(sharedPreferenceKeyObtenerPerfil));
    }

    public static Perfil ObtenerPerfil(Context context) {
        Perfil salida = new Perfil();
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        String listaSerializada = sharedPreferences.getString(sharedPreferenceKeyObtenerPerfil, null);
        if (listaSerializada != null) {
            try {
                byte[] listaBytes = Base64.decode(listaSerializada, Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(listaBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Perfil listaObjeto = (Perfil) objectInputStream.readObject();
                salida = listaObjeto;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return salida;
    }

    public static boolean ExistePerfil(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        return (sharedPreferences.contains(sharedPreferenceKeyObtenerPerfil));
    }

    public static boolean ActualizarPerfil(Context context, Perfil perfil) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(sharedPreferenceKeyObtenerPerfil);
        editor.apply();
        if (sharedPreferences.contains(sharedPreferenceKeyObtenerPerfil)) {
            //-->   Si sigue en el contenedor es por que no se elimino correctamente    <--

            return false;
        }
        return GuardarPerfil(context, perfil);
    }

    public static void GuardarMateriaCursando(Context context, NMateriasCursando materia) {

        ArrayList<NMateriasCursando> materiasCursandoArrayList = ObtencionObtenerMateriasCursando(context);
        materiasCursandoArrayList.add(materia);
        GuardarMateriasCursando(context, materiasCursandoArrayList);
    }

    public static ArrayList<NMateria> ObtencionProgramaAnalitico(Context context) {
        ArrayList<NMateria> salida = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        String listaSerializada = sharedPreferences.getString(sharedPreferenceKeyObtenerProgramaAnal, null);
        if (listaSerializada != null) {
            try {
                byte[] listaBytes = Base64.decode(listaSerializada, Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(listaBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                ArrayList<NMateria> listaObjetos = (ArrayList<NMateria>) objectInputStream.readObject();
                salida = listaObjetos;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return salida;
    }

    public static void GuardarProgramaAnalitico(ArrayList<NMateria> analiticos, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferenceNameContainer, Context.MODE_PRIVATE);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(analiticos);
            String listaSerializadaGuardar = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(sharedPreferenceKeyObtenerProgramaAnal, listaSerializadaGuardar);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //-->   True : Se guardo correctamente -- False : No se guardo  <--
        sharedPreferences.contains(sharedPreferenceKeyObtenerProgramaAnal);
    }
}
package com.example.miutn;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.miutn.network.models.NMateriasCursando;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** @noinspection unused*/
public class ControlaGuardado implements Callback {
    static public NMateriasCursando materia = new NMateriasCursando();
    public Context context;

    public void ConsultaApi() {

    }

    ControlaGuardado(Context context) {
        this.context = context;
    }

    @Override
    public void onResponse(@NonNull Call call, Response response) {
        Log.e("Error", "Aun bien");
        if (response.isSuccessful()) {
            materia = (NMateriasCursando) response.body();
            //-->   TODO    Debo añadirla a las materias que estoy cursando <--
            ControlDatos.GuardarMateriaCursando(context, materia);

        }
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull Throwable t) {
        Log.e("Error", "PERO BIEN");
    }
}

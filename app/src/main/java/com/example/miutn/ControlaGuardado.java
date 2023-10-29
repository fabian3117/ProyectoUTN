package com.example.miutn;

import android.content.Context;
import android.net.DnsResolver;
import android.util.Log;

import com.example.miutn.network.api.ApiService;
import com.example.miutn.network.models.NMateria;
import com.example.miutn.network.models.NMateriasCursando;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControlaGuardado implements Callback {
    static public NMateriasCursando materia=new NMateriasCursando();
    public Context context;
    public void ConsultaApi(){

    }
    ControlaGuardado(Context context){
this.context=context;
    }
    @Override
    public void onResponse(Call call, Response response) {
    Log.e("Error","Aun bien");
    if(response.isSuccessful()){
        materia=(NMateriasCursando) response.body();
        //-->   TODO    Debo añadirla a las materias que estoy cursando <--
        ControlDatos.GuardarMateriaCursando(context,materia);

    }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Log.e("Error","PERO BIEN");
    }
}

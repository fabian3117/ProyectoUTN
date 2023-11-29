package com.example.miutn;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.miutn.notifications.Notificaciones;

public class pruebaAutomatizacion extends Worker  {

    public pruebaAutomatizacion(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("Segundo","PLANO");
        Notificaciones notificaciones=new Notificaciones();
        notificaciones.showNotification(getApplicationContext(),"Prueba","Prueba");
        return Result.success();
    }
}

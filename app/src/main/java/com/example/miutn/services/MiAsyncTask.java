package com.example.miutn.services;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.job.JobParameters;
import android.os.AsyncTask;
import android.util.Log;

public class MiAsyncTask extends AsyncTask<JobParameters, Void, Void> {
    @Override
    protected Void doInBackground(JobParameters... params) {
        // Realizar la tarea programada en segundo plano

        // Supongamos que la tarea se ejecuta con éxito y no es necesario reprogramarla
       // jobFinished(params[0], false);

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // Puedes realizar acciones adicionales después de que se completa la tarea
    }
}

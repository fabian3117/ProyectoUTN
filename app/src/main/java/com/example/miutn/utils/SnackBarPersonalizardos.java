package com.example.miutn.utils;

import android.view.View;

import com.example.miutn.R;
import com.google.android.material.snackbar.Snackbar;

public class SnackBarPersonalizardos {
    Snackbar snackbar;
    public SnackBarPersonalizardos(View view){
        snackbar=Snackbar.make(view,"",Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout snackbarLayout= (Snackbar.SnackbarLayout) snackbar.getView();
//        View snackView = view.getRootView().getContext().getLayoutInflater().inflate(R.layout.snackbar_layout, null);
    }
}

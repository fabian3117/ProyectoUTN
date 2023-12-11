package com.example.miutn.activitys;

import static android.view.View.GONE;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.Spanned;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.miutn.ControlDatos;
import com.example.miutn.R;
import com.example.miutn.genericControlers.ManejoArchivos;
import com.example.miutn.databinding.ActivityVistaMarkdownBinding;
import com.example.miutn.network.api.ApiService;
import com.example.miutn.network.api.RetrofitClient;
import com.example.miutn.network.models.Perfil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.commonmark.node.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

import io.noties.markwon.Markwon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

//todo Modoficar par autilizar firebase <--
public class VistaMarkdown extends AppCompatActivity {
    LottieAnimationView dynamicAnimationView;
    Retrofit retrofit = RetrofitClient.getClient();
    ApiService apiService = retrofit.create(ApiService.class);

    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler(Looper.myLooper());
    TextView fullscreen_content;
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar
            if (Build.VERSION.SDK_INT >= 30) {
                mContentView.getWindowInsetsController().hide(
                        WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
            } else {
                // Note that some of these constants are new as of API 16 (Jelly Bean)
                // and API 19 (KitKat). It is safe to use them, as they are inlined
                // at compile-time and do nothing on earlier devices.
                mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            //-->   Esto es para que se oculte el menu de abajo
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (AUTO_HIDE) {
                        delayedHide(AUTO_HIDE_DELAY_MILLIS);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    break;
                default:
                    break;
            }
            return false;
        }
    };
    private ActivityVistaMarkdownBinding binding;
    ManejoArchivos controladorArchivos=new ManejoArchivos();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVistaMarkdownBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent=getIntent();
        String namedocument;
        dynamicAnimationView= new LottieAnimationView(this);
        if(intent!=null){
            String ts=intent.getStringExtra("documentID");
            if(ts!=null){
                namedocument=ts;
            } else {
                namedocument = "prueba.md";
            }
        } else {
            namedocument = "prueba.md";
        }
        mVisible = true;
        mControlsView = binding.fullscreenContentControls;
        mContentView = binding.fullscreenContent;
        fullscreen_content=binding.fullscreenContent;
        String markdownTest2=controladorArchivos.leerArchivo(getApplicationContext(),namedocument);
      //  Log.e("ARCHIVOS",markdownTest2);
        Markwon markwon = Markwon.create(this);
        //-->   Tengo que generarme un brodcast <--
        //todo brodcast para poder informar cuando se descargo el archivo y visualizarlo    <--


        //-->   Obtenciond el archivo  <--<--
        //con firebase
     //   ManejoArchivos manejoArchivos=new ManejoArchivos();
      //  namedocument="IngresoFuncionLineal.md";
        try {
            TestDescargarArchivos(namedocument,getApplicationContext());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //final Node node = markwon.parse(markdownTest2);
       // final Spanned markdown = markwon.render(node);
       // markwon.setMarkdown(binding.sss, markdownTest2);
  //      markwon.setMarkdown(binding.fullscreenContent, markdownTest2);
     //   markwon.setParsedMarkdown(binding.fullscreenContent, markdown);

        // binding.fullscreenContent.setText(markdownTest);
        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //--> Cuando toca el texto    <--
                toggle();
            }
        });
        binding.dummyButton.setOnTouchListener(mDelayHideTouchListener);
        AtomicBoolean entrada= new AtomicBoolean(false);
        binding.dummyButton.setOnClickListener(v->{
            //-->   Lanzar animacion de carga   <--
            if(!entrada.get()){
                showAnimation();
                entrada.set(true);
                Perfil perfil=ControlDatos.ObtenerPerfil(getApplicationContext());
                //todo aun falta terminar de informar cuando el tema fue visualizado ya.
                /*

                apiService.temaVisto(namedocument, perfil).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Log.e("MIRA","ENTRAMOS TENEMOS QUE INFORMAR PROGRESO");

                        }
                        else{
                            Log.e("MIRA","ENTRAMOS PERO ALGO FALLO VER CODIGO "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("MIRA","ERROR");
                    }
                });

                Log.e("MIRA","ENTRAMOS TENEMOS QUE INFORMAR PROGRESO");
                //-->   Buscar en el temario asociados con ese id y dar por visto el tema   <--
                //-->   Informar al servidor que se ha visto el tema   <--
                //-->   En caso de respuesta satisfactoria realizar la modificacion <--


                 */

            //    binding.fullscreenContentControls.getRootView().get
            }
        });
    }

    public void TestDescargarArchivos(String archivo,Context context) throws IOException {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = firebaseStorage.getReference();
        StorageReference pathReference = storageRef.child(archivo);
        String nombreArchivo = archivo.replace(".md", "").replaceAll("[^a-zA-Z0-9.-]", "_");
        File localFile = File.createTempFile(archivo.replace(".md",""), "md");
        ManejoArchivos manejoArchivos=new ManejoArchivos();
        pathReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(localFile));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    String contenidoArchivo = stringBuilder.toString();
                    Log.e("MIRA",contenidoArchivo);
                    Markwon markwon = Markwon.create(getApplicationContext());
                    final Node node = markwon.parse(contenidoArchivo);
                    final Spanned markdown = markwon.render(node);
                    binding.fullscreenContent.setText(markdown);
                    markwon.setParsedMarkdown(binding.fullscreenContent, markdown);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("FIREBASE","ERROR");
                Snackbar snackbar=Snackbar.make(getCurrentFocus(),"FALLO EN CONEXION", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

    }


    private void showAnimation() {
        //-->   todo Optimizar esto   <--
        // Crear dinámicamente una LottieAnimationView

        dynamicAnimationView.setAnimation(R.raw.loading);
//        dynamicAnimationView.setAnimation("loading.json"); // Reemplaza con el nombre de tu archivo Lottie

        // Ajustar parámetros de diseño para que la vista ocupe toda la pantalla
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        // Limpiar el contenedor antes de agregar la vista

        binding.fullscreenContentControls.removeAllViews();

        // Agregar la vista al contenedor
        binding.fullscreenContentControls.addView(dynamicAnimationView, layoutParams);

        // Hacer la vista visible
        dynamicAnimationView.setVisibility(View.VISIBLE);

        // Reproducir la animación
        dynamicAnimationView.playAnimation();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        // Show the system bar
        if (Build.VERSION.SDK_INT >= 30) {
            mContentView.getWindowInsetsController().show(
                    WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
        } else {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
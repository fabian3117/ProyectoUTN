package com.example.miutn;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    //TODO Aca probare la barra de busquedas a ver si logro hacerla funcionar   <--

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView view = findViewById(R.id.TextPrueba);
        View ViewTextPorcentaje = findViewById(R.id.ViewTextPorcentaje);
        LinearLayout LinearLayoutControl = findViewById(R.id.LinearLayoutControl);
        ViewGroup.LayoutParams parametrosControl = ViewTextPorcentaje.getLayoutParams();
        ViewGroup.LayoutParams parametrosLinearlayout = LinearLayoutControl.getLayoutParams();
        //-->   Test Animation  <--
        //-->   Indicamos valores de inicio y fin   <--
        ValueAnimator animator = ValueAnimator.ofInt(0, parametrosLinearlayout.width);
        animator.setInterpolator(new LinearInterpolator()); //-->   Como interpolar los valores de la animacion <--

        animator.setDuration(1000); //-->   Duracion    <--
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                //-->   Indica el porcentaje de 0-1 en el cual se completo la animacion <--
                int animatedValue = (int) animation.getAnimatedValue();
                parametrosControl.width = animatedValue;
                ViewTextPorcentaje.setLayoutParams(parametrosControl);
                view.setText(String.valueOf(animatedValue));
                //ViewGroup.LayoutParams params = view.getLayoutParams();
                //params.width = animatedValue;
                //view.setLayoutParams(params);
            }
        });
        animator.start();
//        view.setupWithSearchBar(view);
    }
}
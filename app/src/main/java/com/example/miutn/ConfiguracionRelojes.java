package com.example.miutn;

import static com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.fragment.app.FragmentManager;

import com.google.android.material.chip.Chip;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

/** @noinspection FieldMayBeFinal, FieldMayBeFinal, unused */
public class ConfiguracionRelojes implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private Chip chip;
    private FragmentManager fragmentManager;

    public ConfiguracionRelojes(Chip chip, FragmentManager fragmentManager) {
        this.chip = chip;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onClick(View v) {
        MaterialTimePicker picker =
                new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(12)
                        .setMinute(10)
                        .setTitleText("Hora Inicio")
                        .setInputMode(INPUT_MODE_CLOCK)
                        .build();
        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Log.e("mira", "HS:MM " + picker.getHour() + ":" + picker.getMinute());
                //  horaInicio=""+picker.getHour()+":"+picker.getMinute();
                chip.setText("Inicio " + picker.getHour() + ":" + picker.getMinute());
            }
        });
        picker.show(fragmentManager, "tag");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.e("MIRA", "AS");
    }
}

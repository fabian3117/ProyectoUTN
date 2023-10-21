package com.example.miutn.network.models;

import com.example.miutn.network.models.Materia;

import java.util.Date;

public class FechasExamenes {
    private String Fecha;
    private Materia materia;

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
}

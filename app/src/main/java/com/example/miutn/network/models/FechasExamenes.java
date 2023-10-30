package com.example.miutn.network.models;

import java.io.Serializable;

/** @noinspection unused, unused */
public class FechasExamenes implements Serializable {
    private String Fecha;
    private NMateria materia;

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public NMateria getMateria() {
        return materia;
    }

    public void setMateria(NMateria materia) {
        this.materia = materia;
    }
}

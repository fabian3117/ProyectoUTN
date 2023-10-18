package com.example.miutn;

import com.example.miutn.network.models.Materia;

import java.util.Date;

public class FechasExamenes {
    private Date Fecha;
    private Materia materia;

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
}

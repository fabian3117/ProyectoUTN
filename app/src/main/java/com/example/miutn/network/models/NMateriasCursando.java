package com.example.miutn.network.models;

import java.io.Serializable;

public class NMateriasCursando implements Serializable {
    private String id;
    private NMateria materia;
    private Horarios horario;

    public NMateriasCursando() {
        materia=new NMateria();
        horario=new Horarios();
        id="";
    }

    public NMateriasCursando(String id,NMateria materia, Horarios horario) {
        this.id=id;
        this.materia = materia;
        this.horario = horario;
    }

    public NMateria getMateria() {
        return materia;
    }

    public void setMateria(NMateria materia) {
        this.materia = materia;
    }

    public Horarios getHorario() {
        return horario;
    }

    public void setHorario(Horarios horario) {
        this.horario = horario;
    }
}

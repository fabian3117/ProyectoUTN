package com.example.miutn.network.models;

import com.example.miutn.enums.Sedes;

import java.io.Serializable;

public class Horarios implements Serializable {
    private String dia,horaInicio,horaFin,aula;
    private Sedes sede=Sedes.Campus;


    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public Sedes getSede() {
        return sede;
    }

    public void setSede(Sedes sede) {
        this.sede = sede;
    }

    public Horarios() {
        this.dia = "";
        this.horaInicio = "";
        this.horaFin = "";
        this.aula = "";
        this.sede = Sedes.Campus;
    }

    public Horarios(String dia, String horaInicio, String horaFin, String aula, Sedes sede) {
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.aula = aula;
        this.sede = sede;
    }
}

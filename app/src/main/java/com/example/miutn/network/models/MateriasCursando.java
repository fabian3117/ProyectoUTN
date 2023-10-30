package com.example.miutn.network.models;

import java.io.Serializable;

/** @noinspection unused, unused, unused, unused, unused, unused, unused, unused, unused, unused, unused, unused, unused, unused, unused */
public class MateriasCursando implements Serializable {
    String id;
    private Materia materia;
    private String aula;
    private String dia;
    private String horario;
    private String sede;
    private int temasVistos;

    public int getTemasVistos() {
        return temasVistos;
    }

    public void setTemasVistos(int temasVistos) {
        this.temasVistos = temasVistos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }


    public MateriasCursando() {
        this.materia = new Materia();
        this.aula = "";
        this.dia = "";
        this.horario = "";
        this.sede = "";
        temasVistos = 0;
    }
}

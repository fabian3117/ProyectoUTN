package com.example.miutn.network.models;

import com.example.miutn.enums.Cuatrimestres;
import com.example.miutn.enums.Modalidad;
import com.example.miutn.enums.Sedes;

import java.io.Serializable;
import java.util.ArrayList;

public class NMateria implements Serializable {
    private String id;
    private String name,anio;
    private NprogramaAnalitico programaAnalitico;

    private Cuatrimestres cuatrimestre;
    private Modalidad modalidad;
    private ArrayList<String> correlativas;//-->    Para cursar <--

    public NMateria(String id,String name, String anio, NprogramaAnalitico programaAnalitico, Cuatrimestres cuatrimestre, Modalidad modalidad, ArrayList<String> correlativas) {
        this.name = name;
        this.anio = anio;
        this.id=id;
        this.programaAnalitico = programaAnalitico;
        this.cuatrimestre = cuatrimestre;
        this.modalidad = modalidad;
        this.correlativas = correlativas;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public NprogramaAnalitico getProgramaAnalitico() {
        return programaAnalitico;
    }

    public void setProgramaAnalitico(NprogramaAnalitico programaAnalitico) {
        this.programaAnalitico = programaAnalitico;
    }

    public Cuatrimestres getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(Cuatrimestres cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public ArrayList<String> getCorrelativas() {
        return correlativas;
    }

    public void setCorrelativas(ArrayList<String> correlativas) {
        this.correlativas = correlativas;
    }

    public NMateria() {
        this.name = "";
        this.anio = "";
        this.id="";
        this.programaAnalitico = new NprogramaAnalitico();
        this.cuatrimestre = Cuatrimestres.PrimerCuatrimestre;
        this.modalidad = Modalidad.Presencial;
        this.correlativas = new ArrayList<>();
    }
}

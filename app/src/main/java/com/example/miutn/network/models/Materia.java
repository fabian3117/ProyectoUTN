package com.example.miutn.network.models;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

/** @noinspection unused, unused, unused, unused, unused, unused, unused, unused, unused, unused, unused, unused, unused, unused, unused, unused, unused, unused, unused, unused */
@Data
public class Materia implements Serializable {
    private String id;
    private String nombre;
    private int anio;
    private int cuatri;
    private ArrayList<String> carreras; //-->   Carreras asociadas  <--
    //-->   Requisitos para cursada <--
    private ArrayList<String> materiasCursadas;
    private ArrayList<String> materiasAprobadas;
    //-->   Requisitos para final   <--
    private ArrayList<String> materiasCursadasFinal;
    private ArrayList<String> materiasAprobadasFinal;
    private ArrayList<Temario> programaAnalitico;

    public Materia() {
        programaAnalitico = new ArrayList<>();
        materiasAprobadas = new ArrayList<>();
        materiasCursadas = new ArrayList<>();
        materiasAprobadasFinal = new ArrayList<>();
        materiasCursadasFinal = new ArrayList<>();
        carreras = new ArrayList<>();
        anio = 0;
    }

    public ArrayList<Temario> getProgramaAnalitico() {
        return programaAnalitico;
    }

    public void setProgramaAnalitico(ArrayList<Temario> programaAnalitico) {
        this.programaAnalitico = programaAnalitico;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getCuatri() {
        return cuatri;
    }

    public void setCuatri(int cuatri) {
        this.cuatri = cuatri;
    }

    public ArrayList<String> getCarreras() {
        return carreras;
    }

    public void setCarreras(ArrayList<String> carreras) {
        this.carreras = carreras;
    }

    public ArrayList<String> getMateriasCursadas() {
        return materiasCursadas;
    }

    public void setMateriasCursadas(ArrayList<String> materiasCursadas) {
        this.materiasCursadas = materiasCursadas;
    }

    public ArrayList<String> getMateriasAprobadas() {
        return materiasAprobadas;
    }

    public void setMateriasAprobadas(ArrayList<String> materiasAprobadas) {
        this.materiasAprobadas = materiasAprobadas;
    }

    public ArrayList<String> getMateriasCursadasFinal() {
        return materiasCursadasFinal;
    }

    public void setMateriasCursadasFinal(ArrayList<String> materiasCursadasFinal) {
        this.materiasCursadasFinal = materiasCursadasFinal;
    }

    public ArrayList<String> getMateriasAprobadasFinal() {
        return materiasAprobadasFinal;
    }

    public void setMateriasAprobadasFinal(ArrayList<String> materiasAprobadasFinal) {
        this.materiasAprobadasFinal = materiasAprobadasFinal;
    }
}

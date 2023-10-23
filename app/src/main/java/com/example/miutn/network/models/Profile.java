package com.example.miutn.network.models;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

/**
 * This is model of profile
 */

public class Profile  implements Serializable {
    private ArrayList<MateriasCursando> materiasCursandos;
    private ArrayList<MateriasCursando> materiasCursadasAprobadas;
    private ArrayList<MateriasCursando> materiasCursadasRegularizadas;
    private String numberLegajo,userSIU,name,carrera,correoInstitucional;

    public Profile(ArrayList<MateriasCursando> materiasCursandos, ArrayList<MateriasCursando> materiasCursadasAprobadas, ArrayList<MateriasCursando> materiasCursadasRegularizadas, String numberLegajo, String userSIU, String name, String carrera,String correoInstitucional) {
        this.materiasCursandos = materiasCursandos;
        this.materiasCursadasAprobadas = materiasCursadasAprobadas;
        this.materiasCursadasRegularizadas = materiasCursadasRegularizadas;
        this.numberLegajo = numberLegajo;
        this.userSIU = userSIU;
        this.name = name;
        this.carrera = carrera;
        this.correoInstitucional=correoInstitucional;
    }
    public Profile() {
        this.materiasCursandos = new ArrayList<>();
        this.materiasCursadasAprobadas = new ArrayList<>();
        this.materiasCursadasRegularizadas = new ArrayList<>();
        this.numberLegajo = "";
        this.userSIU = "";
        this.name = "";
        this.carrera = "";
        this.correoInstitucional="";
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public ArrayList<MateriasCursando> getMateriasCursandos() {
        return materiasCursandos;
    }

    public void setMateriasCursandos(ArrayList<MateriasCursando> materiasCursandos) {
        this.materiasCursandos = materiasCursandos;
    }

    public ArrayList<MateriasCursando> getMateriasCursadasAprobadas() {
        return materiasCursadasAprobadas;
    }

    public void setMateriasCursadasAprobadas(ArrayList<MateriasCursando> materiasCursadasAprobadas) {
        this.materiasCursadasAprobadas = materiasCursadasAprobadas;
    }

    public ArrayList<MateriasCursando> getMateriasCursadasRegularizadas() {
        return materiasCursadasRegularizadas;
    }

    public void setMateriasCursadasRegularizadas(ArrayList<MateriasCursando> materiasCursadasRegularizadas) {
        this.materiasCursadasRegularizadas = materiasCursadasRegularizadas;
    }

    public String getNumberLegajo() {
        return numberLegajo;
    }

    public void setNumberLegajo(String numberLegajo) {
        this.numberLegajo = numberLegajo;
    }

    public String getUserSIU() {
        return userSIU;
    }

    public void setUserSIU(String userSIU) {
        this.userSIU = userSIU;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
}

package com.example.miutn.network.models;

import com.example.miutn.enums.Carreras;

import java.io.Serializable;
import java.util.ArrayList;

/** @noinspection FieldMayBeFinal, SillyAssignment, DataFlowIssue, UnusedAssignment */
public class Perfil implements Serializable {
    private String id;
    private String userName, password, profilePic, legajo, userSIU, name, correoInstitucional;
    private Carreras carrea;
    //-->   Diferencia entre CURSADAS y CURSANDO es que la primera tienen adjunto los horarios  <--
    private ArrayList<NMateria> materiasCursadas;   //-->   Materias CURSADAS en pasado <--
    private ArrayList<NMateriasCursando> materiasCursando;  //-->   CURSANDO ACTUALMENTE    <--

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public Perfil(String userName, String password, String profilePic, String legajo, String userSIU, Carreras carrea, ArrayList<NMateria> materiasCursadas, ArrayList<NMateriasCursando> materiasCursando, String name, String correoInstitucional) {
        this.userName = userName;
        this.password = password;
        this.profilePic = profilePic;
        this.legajo = legajo;
        this.userSIU = userSIU;
        this.carrea = carrea;
        this.materiasCursadas = materiasCursadas;
        this.materiasCursando = materiasCursando;
        this.name = name;
        this.correoInstitucional = correoInstitucional;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Perfil() {
        this.id = "";
        this.userName = "userName";
        this.password = "password";
        this.profilePic = "profilePic";
        this.legajo = "legajo";
        this.userSIU = "userSIU";
        carrea = Carreras.Electronica;
        this.materiasCursadas = new ArrayList<>();
        this.materiasCursando = new ArrayList<>();
        correoInstitucional = "";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public String getUserSIU() {
        return userSIU;
    }

    public void setUserSIU(String userSIU) {
        this.userSIU = userSIU;
    }

    public Carreras getCarrea() {
        return carrea;
    }

    public void setCarrea(Carreras carrea) {
        this.carrea = carrea;
    }

    public ArrayList<NMateria> getMateriasCursadas() {
        return materiasCursadas;
    }

    public void setMateriasCursadas(ArrayList<NMateria> materiasCursadas) {
        this.materiasCursadas = materiasCursadas;
    }

    public ArrayList<NMateriasCursando> getMateriasCursando() {
        return materiasCursando;
    }

    public void setMateriasCursando(ArrayList<NMateriasCursando> materiasCursando) {
        this.materiasCursando = materiasCursando;
    }
}

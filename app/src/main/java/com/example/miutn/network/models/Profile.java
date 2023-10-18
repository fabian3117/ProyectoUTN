package com.example.miutn.network.models;

import java.util.ArrayList;

import lombok.Data;

/**
 * This is model of profile
 */
@Data
public class Profile {
    private ArrayList<MateriasCursando> materiasCursandos;
    private ArrayList<MateriasCursando> materiasCursadasAprobadas;
    private ArrayList<MateriasCursando> materiasCursadasRegularizadas;
}

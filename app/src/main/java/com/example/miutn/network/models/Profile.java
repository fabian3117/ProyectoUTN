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
}

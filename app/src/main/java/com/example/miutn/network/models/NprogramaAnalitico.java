package com.example.miutn.network.models;

import java.io.Serializable;
import java.util.ArrayList;

public class NprogramaAnalitico implements Serializable {
    private String id;
    private ArrayList<Temario> temas;

    public NprogramaAnalitico(ArrayList<Temario> temas) {
        this.temas = temas;
    }

    public ArrayList<Temario> getTemas() {
        return temas;
    }

    public void setTemas(ArrayList<Temario> temas) {
        this.temas = temas;
    }

    public NprogramaAnalitico() {
    }
}

package com.example.miutn.enums;

public enum Cuatrimestres {
    PrimerCuatrimestre(1),
    SegundoCuatrimestre(2);
    private int valor;
    Cuatrimestres(int valor){
        this.valor=valor;
    }
    public int getValorAsociado(){return valor;}
}

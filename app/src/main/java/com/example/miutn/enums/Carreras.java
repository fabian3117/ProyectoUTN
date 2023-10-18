package com.example.miutn.enums;

public enum Carreras {
    Electronica("IngenieriaElectronica"),
    Mecanica("IngenieriaMecanica");

    private String valor;
    Carreras(String valor) {
        this.valor=valor;
    }
    public String getValorAsociado() {
        return valor;
    }
}

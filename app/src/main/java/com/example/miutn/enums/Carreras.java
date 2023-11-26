package com.example.miutn.enums;

import androidx.annotation.NonNull;

public enum Carreras {
    Electronica("IngenieriaElectronica"),
    Mecanica("IngenieriaMecanica"),
    Ingreso("Ingreso");

    /** @noinspection unused*/
    /** @noinspection FieldMayBeFinal*/
    private String valor;

    Carreras(String valor) {
        this.valor = valor;
    }

    public String getValorAsociado() {
        return valor;
    }

    @NonNull
    @Override
    public String toString() {
        return valor;
    }
}

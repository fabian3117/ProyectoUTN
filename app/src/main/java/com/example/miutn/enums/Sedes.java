package com.example.miutn.enums;

public enum Sedes {
    Campus("Campus"),
    Medrano("Medrano");

    /** @noinspection FieldMayBeFinal*/
    /** @noinspection NonFinalFieldInEnum*/
    private String valor;

    Sedes(String valor) {
        this.valor = valor;
    }

    public String getValorAsociado() {
        return valor;
    }
}

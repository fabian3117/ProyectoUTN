package com.example.miutn.enums;

public enum Modalidad {
    Virtual(1),
    Presencial(2),
    Hibrido(3);
    /** @noinspection FieldMayBeFinal*/
    /** @noinspection NonFinalFieldInEnum*/
    private final int valor;

    Modalidad(int valor) {
        this.valor = valor;
    }

    public int getValorAsociado() {
        return valor;
    }
}

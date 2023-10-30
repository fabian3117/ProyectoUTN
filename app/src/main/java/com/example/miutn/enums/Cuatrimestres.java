package com.example.miutn.enums;

public enum Cuatrimestres {
    PrimerCuatrimestre(1),
    SegundoCuatrimestre(2),

    Anual(3);

    /** @noinspection FieldMayBeFinal*/
    /** @noinspection unused*/
    /** @noinspection unused*/
    /** @noinspection unused*/
    /** @noinspection NonFinalFieldInEnum*/
    private int valor;

    Cuatrimestres(int valor) {
        this.valor = valor;
    }

    public int getValorAsociado() {
        return valor;
    }

}

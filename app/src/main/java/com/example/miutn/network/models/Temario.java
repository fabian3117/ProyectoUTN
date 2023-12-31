package com.example.miutn.network.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data
@Setter
@Getter
public class Temario implements Serializable {
    private String id;
    private String tema;
    private String apunte;
    private String description;
    private LocalDateTime fechaAbierto;
    private LocalDateTime fechaCerrado;  //-->   Tema visto y finalizado <--

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getApunte() {
        return apunte;
    }

    public void setApunte(String apunte) {
        this.apunte = apunte;
    }

    public Temario() {
        this.tema = "tema";
        this.apunte = "apunte";
        this.description = "description";
    }
}

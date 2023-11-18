package com.example.miutn.security;

public class Credenciales {
    String usuario;
    String clave;

    public Credenciales(String usuario, String clave) {
        this.usuario = usuario;
        //-->   Genero el hash y guardo eso <--
        this.clave =CreacionHash.sha256(String.valueOf(clave));

    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave =CreacionHash.sha256(String.valueOf(clave));
    }
}

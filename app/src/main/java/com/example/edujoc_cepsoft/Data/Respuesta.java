package com.example.edujoc_cepsoft.Data;

public class Respuesta {

    private String respuesta;
    private boolean correcte;

    public Respuesta(String respuesta, boolean correcte) {
        this.respuesta = respuesta;
        this.correcte = correcte;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public boolean isCorrecte() {
        return correcte;
    }
}

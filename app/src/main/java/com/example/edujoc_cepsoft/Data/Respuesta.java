package com.example.edujoc_cepsoft.Data;

import java.io.Serializable;

public class Respuesta implements Serializable
{
    private String respuesta;
    private boolean correcta;

    public Respuesta() {}

    public Respuesta(String respuesta, boolean correcta)
    {
        this.respuesta = respuesta;
        this.correcta = correcta;
    }

    //region Getters
    public String getRespuesta() {
        return respuesta;
    }

    public boolean isCorrecta() {
        return correcta;
    }
    //endregion
}

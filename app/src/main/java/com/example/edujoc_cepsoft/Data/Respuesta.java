package com.example.edujoc_cepsoft.Data;

public class Respuesta
{
    private String respuesta;
    private boolean correcta;

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

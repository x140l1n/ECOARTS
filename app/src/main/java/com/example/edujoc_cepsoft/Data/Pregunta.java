package com.example.edujoc_cepsoft.Data;

import java.util.List;

public class Pregunta
{
    private int id;
    private String pregunta;
    private String idioma;
    private String tema;
    private List<Respuesta> respuestas;
    private boolean mostrada = false;

    public Pregunta(int id, String pregunta, String idioma, String tema, List<Respuesta> respuestas)
    {
        this.id = id;
        this.pregunta = pregunta;
        this.idioma = idioma;
        this.tema = tema;
        this.respuestas = respuestas;
    }

    //region Getters & Setters
    public int getId() {
        return id;
    }

    public String getPregunta() {
        return pregunta;
    }


    public String getIdioma() {
        return idioma;
    }

    public String getTema() {
        return tema;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public boolean isMostrada() {
        return mostrada;
    }

    public void setMostrada(boolean mostrada) {
        this.mostrada = mostrada;
    }
    //endregion
}

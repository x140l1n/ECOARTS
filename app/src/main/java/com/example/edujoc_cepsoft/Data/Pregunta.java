package com.example.edujoc_cepsoft.Data;

import java.io.Serializable;
import java.util.List;

public class Pregunta implements Serializable {
    private int id;
    private String pregunta;
    private String idioma;
    private String tema;
    private List<Respuesta> respuestas;
    private boolean mostrada;

    public Pregunta(int id, String pregunta, String idioma, String tema, List<Respuesta> respuestas) {
        this.id = id;
        this.pregunta = pregunta;
        this.idioma = idioma;
        this.tema = tema;
        this.respuestas = respuestas;
        this.mostrada = false;
    }

    //region Getters & Setters
    public int getId() {
        return id;
    }

    public String getPregunta() {
        return pregunta;
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

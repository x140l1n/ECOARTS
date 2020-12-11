package com.example.edujoc_cepsoft.Data;

import java.util.ArrayList;
import java.util.List;

public class Pregunta {
    private int id;
    private String pregunta;
    private String idioma;
    private String Tema;
    private List<Respuesta> respuestas;
    private boolean mostrada = false;

    public Pregunta(int id, String pregunta, String idioma, String tema, List<Respuesta> respuestas) {
        this.id = id;
        this.pregunta = pregunta;
        this.idioma = idioma;
        Tema = tema;
        this.respuestas = respuestas;
    }

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
        return Tema;
    }

    public boolean isMostrada() {
        return mostrada;
    }

    public void setMostrada(boolean mostrada) {
        this.mostrada = mostrada;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }
}

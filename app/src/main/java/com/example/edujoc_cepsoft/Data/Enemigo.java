package com.example.edujoc_cepsoft.Data;

import java.io.Serializable;

public class Enemigo implements Serializable {
    private final int vida_maxima; //La vida máxima que puede tener, cada enemigo tendrá como máximo x vidas.
    private final String nombre;
    private final int imagen;
    private final int colorFondo;
    private int vida; //La vida actual que tiene.

    public Enemigo(int imagen, String nombre, int colorFondo, int vida_maxima) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.colorFondo = colorFondo;
        this.vida_maxima = vida_maxima;
        this.vida = vida_maxima;
    }

    public void quitarVida(int i) {
        this.vida -= i;
    }

    //region Getters & Setters
    public int getImagen() {
        return imagen;
    }

    public int getVida() {
        return vida;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVida_maxima() {
        return this.vida_maxima;
    }

    public int getColorFondo() {
        return colorFondo;
    }
    //endregion
}

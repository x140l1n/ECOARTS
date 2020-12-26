package com.example.edujoc_cepsoft.Data;

import java.io.Serializable;

public class Enemigo implements Serializable
{
    private int vida_maxima; //La vida máxima que puede tener.
    private String nombre;
    private int imagen;
    private int vida; //La vida actual que tiene.
    private int colorFondo;

    public Enemigo(int imagen, String nombre, int colorFondo, int vida_maxima)
    {
        this.imagen = imagen;
        this.nombre = nombre;
        this.colorFondo = colorFondo;
        this.vida_maxima = vida_maxima;
        this.vida = vida_maxima;
    }

    public void quitarVida(int i)
    {
        this.vida -= i;
    }

    //region Getters & Setters
    public int getImagen()
    {
        return imagen;
    }

    public int getVida()
    {
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

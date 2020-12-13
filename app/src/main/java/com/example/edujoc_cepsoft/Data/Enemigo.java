package com.example.edujoc_cepsoft.Data;

import java.io.Serializable;

public class Enemigo implements Serializable
{
    private final int VIDA_MAXIMA = 5; //La vida máxima que puede tener.

    private String nombre;
    private int imagen;
    private int vidas = VIDA_MAXIMA; //La vida actual que tiene.

    public Enemigo(int imagen, String nombre)
    {
        this.imagen = imagen;
        this.nombre = nombre;
    }

    public void quitarVida(int i)
    {
        this.vidas -= i;
    }

    //region Getters & Setters
    public int getImagen()
    {
        return imagen;
    }

    public void setImagen(int imagen)
    {
        this.imagen = imagen;
    }

    public int getVidas()
    {
        return vidas;
    }

    public void setVidas(int vidas)
    {
        this.vidas = vidas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVIDA_MAXIMA() {
        return this.VIDA_MAXIMA;
    }
    //endregion
}

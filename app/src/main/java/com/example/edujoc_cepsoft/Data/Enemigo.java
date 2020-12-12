package com.example.edujoc_cepsoft.Data;

public class Enemigo
{
    private String nombre;
    private int imagen;
    private int vidas = 5; //Tienen como máximo 5 vidas.

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
    //endregion
}

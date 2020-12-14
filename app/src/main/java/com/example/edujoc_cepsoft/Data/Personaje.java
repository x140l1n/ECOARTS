package com.example.edujoc_cepsoft.Data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.Serializable;

public class Personaje implements Serializable
{
    private final int VIDA_MAXIMA = 5; //La vida máxima que puede tener.

    private int id;
    private String nombre;
    private String idioma;
    private String rutaImagen;
    private String descripcion;

    private boolean seleccionado = false;
    private int vidas = VIDA_MAXIMA; //La vida actual que tiene.

    public Personaje ()
    {}

    public Personaje(int id, String nombre, String idioma, String rutaImagen, String descripcion)
    {
        this.id = id;
        this.nombre = nombre;
        this.idioma = idioma;
        this.rutaImagen = rutaImagen;
        this.descripcion = descripcion;
    }

    public void quitarVida(int i)
    {
        this.vidas -= i;
    }

    //region Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public int getVIDA_MAXIMA() {
        return this.VIDA_MAXIMA;
    }
    //endregion

    /**
     * Obtener una imagen a partir de una ruta.
     * @param context El activity de donde llama a este método.
     * @return La imagen del personaje.
     */
    public Bitmap obtenerImagen(Context context)
    {
        //Obtener la ruta de la imagen del objeto personaje, replazamos el ./ por la ruta files de la app y convertimos todo en minúsculas.
        String rutaAbsolutaPersonaje = this.getRutaImagen().replace("./", context.getFilesDir() + File.separator).toLowerCase();

        //Obtener la imagen de la ruta y crear una imagen Bitmap.
        Bitmap bitmapPersonaje = BitmapFactory.decodeFile(rutaAbsolutaPersonaje);

        return bitmapPersonaje;
    }
}

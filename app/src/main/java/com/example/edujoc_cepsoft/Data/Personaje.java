package com.example.edujoc_cepsoft.Data;

public class Personaje
{
    private int id;
    private String nombre;
    private String idioma;
    private String rutaImagen;
    private String descripcion;

    public Personaje(int id, String nombre, String idioma, String rutaImagen, String descripcion)
    {
        this.id = id;
        this.nombre = nombre;
        this.idioma = idioma;
        this.rutaImagen = rutaImagen;
        this.descripcion = descripcion;
    }

    //region Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdioma() {
        return idioma;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public String getDescripcion() {
        return descripcion;
    }
    //endregion
}

package com.example.edujoc_cepsoft.Data;

public class PaginaTutorial
{
    private int imagen;
    private String texto;

    public PaginaTutorial(int imagen, String texto)
    {
        this.imagen = imagen;
        this.texto = texto;
    }

    //region Getters
    public int getImagen() {
        return imagen;
    }

    public String getTexto() {
        return texto;
    }
    //endregion
}

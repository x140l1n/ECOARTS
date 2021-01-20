package com.example.edujoc_cepsoft.Data;

public class PaginaTutorial
{
    private final int imagen;
    private final String texto;

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

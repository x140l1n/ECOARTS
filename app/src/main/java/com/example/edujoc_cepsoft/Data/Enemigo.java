package com.example.edujoc_cepsoft.Data;

public class Enemigo {

    private int vidas = 5;
    private int imagenDrawable;

    public Enemigo(int imagenDrawable) {
        this.imagenDrawable = imagenDrawable;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }
}

package com.example.edujoc_cepsoft;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends MiActivityPersonalizado {
    @Override
    @SuppressWarnings("deprecation") //Eliminar la advertencia de usar métodos en desuso.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Crea un nuevo hilo y se ejecutará pasadas 2000 milisegundos (2 segundos).
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                IdiomaActivity.activity_anterior = "main";

                startActivity(new Intent(MainActivity.this, IdiomaActivity.class));
                finish();
            }
        }, 2000);
    }
}
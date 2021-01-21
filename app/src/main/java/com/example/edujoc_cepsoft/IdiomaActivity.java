package com.example.edujoc_cepsoft;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.edujoc_cepsoft.Helpers.EffectSoundHelper;
import com.example.edujoc_cepsoft.Helpers.GifHelper;
import com.example.edujoc_cepsoft.Helpers.LocaleHelper;

public class IdiomaActivity extends MiActivityPersonalizado {
    //Identificar cuál es el activity anterior, dependiendo de cuál es el activity anterior, el botón de idiomas nos mandará al activity introducción o al activity ajustes.
    public static String activity_anterior = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idioma);

        //Iniciamos la música del menú.
        id_musica = R.raw.menu;

        if (id_musica != 0 && activity_anterior.equals("main")) {
            musicaFondo = MediaPlayer.create(this, id_musica);

            if (sonarMusica) musicaFondo.setVolume(0.5f, 0.5f);
            else musicaFondo.setVolume(0f, 0f);

            musicaFondo.setLooping(true);
            musicaFondo.start();
        }

        GifHelper.loadGif(this, R.drawable.fondo_principal_animado, (ImageView) findViewById(R.id.fondoGif));

        final Button btnEs = findViewById(R.id.btnCastellano);
        final Button btnCa = findViewById(R.id.btnCatala);
        final Button btnEn = findViewById(R.id.btnEnglish);
        ImageButton btnVolver = findViewById(R.id.btnVolver);

        if (activity_anterior.equals("main")) btnVolver.setVisibility(View.GONE);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EffectSoundHelper.reproducirEfecto(IdiomaActivity.this, R.raw.boton_click);

                startActivity(new Intent(IdiomaActivity.this, AjustesActivity.class));

                finish();
            }
        });

        btnEs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EffectSoundHelper.reproducirEfecto(IdiomaActivity.this, R.raw.boton_click);

                LocaleHelper.setLocale(IdiomaActivity.this, "es");

                if (activity_anterior.equals("main")) {
                    IntroduccionActivity.activity_anterior = "idioma";

                    startActivity(new Intent(IdiomaActivity.this, IntroduccionActivity.class));
                } else {
                    startActivity(new Intent(IdiomaActivity.this, AjustesActivity.class));
                }

                finish();
            }
        });

        btnCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EffectSoundHelper.reproducirEfecto(IdiomaActivity.this, R.raw.boton_click);

                LocaleHelper.setLocale(IdiomaActivity.this, "ca");

                if (activity_anterior.equals("main")) {
                    IntroduccionActivity.activity_anterior = "idioma";

                    startActivity(new Intent(IdiomaActivity.this, IntroduccionActivity.class));
                } else {
                    startActivity(new Intent(IdiomaActivity.this, AjustesActivity.class));
                }

                finish();
            }
        });

        btnEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EffectSoundHelper.reproducirEfecto(IdiomaActivity.this, R.raw.boton_click);

                LocaleHelper.setLocale(IdiomaActivity.this, "en");

                if (activity_anterior.equals("main")) {
                    IntroduccionActivity.activity_anterior = "idioma";

                    startActivity(new Intent(IdiomaActivity.this, IntroduccionActivity.class));
                } else {
                    startActivity(new Intent(IdiomaActivity.this, AjustesActivity.class));
                }

                finish();
            }
        });
    }
}
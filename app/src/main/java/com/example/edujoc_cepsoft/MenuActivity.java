package com.example.edujoc_cepsoft;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edujoc_cepsoft.Data.PaginaTutorial;
import com.example.edujoc_cepsoft.Helpers.EffectSoundHelper;
import com.example.edujoc_cepsoft.Helpers.GifHelper;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends MiActivityPersonalizado {
    private int pagina_tutorial = 0;

    public static String activity_anterior = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        GifHelper.loadGif(this, R.drawable.fondo_principal_animado, (ImageView) findViewById(R.id.fondoGif));

        if (activity_anterior.equals("mapa") || activity_anterior.equals("batalla"))
        {
            //Iniciamos la música del menú.
            id_musica = R.raw.menu;

            if (id_musica != 0) {
                musicaFondo = MediaPlayer.create(this, id_musica);

                if (sonarMusica) musicaFondo.setVolume(0.5f, 0.5f);
                else musicaFondo.setVolume(0f, 0f);

                musicaFondo.setLooping(true);
                musicaFondo.start();
            }
        }

        activity_anterior = "";

        Button btnJugar = findViewById(R.id.btnJugar);
        Button btnAjustes = findViewById(R.id.btnAjustes);
        Button btnSalir = findViewById(R.id.btnSalir);
        Button btnTutorial = findViewById(R.id.btnTutorial);

        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EffectSoundHelper.reproducirEfecto(MenuActivity.this, R.raw.boton_click);

                startActivity(new Intent(MenuActivity.this, SelectPersonajeActivity.class));

                finish();
            }
        });

        btnAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EffectSoundHelper.reproducirEfecto(MenuActivity.this, R.raw.boton_click);

                startActivity(new Intent(MenuActivity.this, AjustesActivity.class));

                finish();
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EffectSoundHelper.reproducirEfecto(MenuActivity.this, R.raw.boton_click);

                final Dialog dialogSalir = new MiDialogPersonalizado(MenuActivity.this, R.layout.dialog_salir);

                ImageButton btnSalir = dialogSalir.findViewById(R.id.btnSalir);
                ImageButton btnCancelar = dialogSalir.findViewById(R.id.btnCancelar);

                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EffectSoundHelper.reproducirEfecto(MenuActivity.this, R.raw.boton_click);

                        dialogSalir.dismiss();
                    }
                });

                btnSalir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EffectSoundHelper.reproducirEfecto(MenuActivity.this, R.raw.boton_click);

                        dialogSalir.dismiss();

                        if (musicaFondo != null) {
                            id_musica = 0;
                            musicaFondo.stop();
                            musicaFondo = null;
                        }

                        finishAffinity();
                    }
                });

                dialogSalir.show();
            }
        });

        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EffectSoundHelper.reproducirEfecto(MenuActivity.this, R.raw.boton_click);

                final Dialog dialog = new MiDialogPersonalizado(MenuActivity.this, R.layout.dialog_tutorial);

                final List<PaginaTutorial> paginas = new ArrayList<>();
                paginas.add(new PaginaTutorial(R.drawable.ecoartslogo_sin_eslogan, getString(R.string.introduccuion_tutorial)));
                paginas.add(new PaginaTutorial(R.drawable.tutorial_1, getString(R.string.tutorial_1)));
                paginas.add(new PaginaTutorial(R.drawable.tutorial_2, getString(R.string.tutorial_2)));
                paginas.add(new PaginaTutorial(R.drawable.tutorial_3, getString(R.string.tutorial_3)));

                ImageButton btnCancelar = dialog.findViewById(R.id.btnCancelar);

                final ImageButton btnAnterior = dialog.findViewById(R.id.btnAnterior);
                final ImageButton btnSiguiente = dialog.findViewById(R.id.btnSiguiente);

                final ImageView imagenTutorial = dialog.findViewById(R.id.imagenTutorial);
                final TextView textViewTutorial = dialog.findViewById(R.id.textViewTutorial);

                //Primero mostramos la introducción.
                PaginaTutorial paginaTutorial = paginas.get(pagina_tutorial);

                imagenTutorial.setImageResource(paginaTutorial.getImagen());
                textViewTutorial.setText(paginaTutorial.getTexto());

                btnAnterior.setVisibility(View.INVISIBLE);

                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EffectSoundHelper.reproducirEfecto(MenuActivity.this, R.raw.boton_click);

                        dialog.dismiss();
                    }
                });

                btnAnterior.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EffectSoundHelper.reproducirEfecto(MenuActivity.this, R.raw.boton_click);

                        //Comprobamos la anterior página no sea menor que 0.
                        if (pagina_tutorial - 1 >= 0) {
                            pagina_tutorial--;

                            PaginaTutorial paginaTutorial = paginas.get(pagina_tutorial);

                            imagenTutorial.setImageResource(paginaTutorial.getImagen());
                            textViewTutorial.setText(paginaTutorial.getTexto());

                            if (pagina_tutorial == 0) btnAnterior.setVisibility(View.INVISIBLE);
                            else btnAnterior.setVisibility(View.VISIBLE);

                            btnSiguiente.setVisibility(View.VISIBLE);
                        }
                    }
                });

                btnSiguiente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EffectSoundHelper.reproducirEfecto(MenuActivity.this, R.raw.boton_click);

                        //Comprobamos la siguiente página no sea mayor que el número de paginas.
                        if (pagina_tutorial + 1 < paginas.size()) {
                            pagina_tutorial++;

                            PaginaTutorial paginaTutorial = paginas.get(pagina_tutorial);

                            imagenTutorial.setImageResource(paginaTutorial.getImagen());
                            textViewTutorial.setText(paginaTutorial.getTexto());

                            if (pagina_tutorial == paginas.size() - 1)
                                btnSiguiente.setVisibility(View.INVISIBLE);
                            else btnSiguiente.setVisibility(View.VISIBLE);

                            btnAnterior.setVisibility(View.VISIBLE);
                        }
                    }
                });

                dialog.show();
            }
        });
    }
}
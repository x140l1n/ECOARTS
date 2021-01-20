package com.example.edujoc_cepsoft;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.edujoc_cepsoft.Helpers.EffectSoundHelper;
import com.example.edujoc_cepsoft.Helpers.GifHelper;

public class AjustesActivity extends MiActivityPersonalizado {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        GifHelper.loadGif(this, R.drawable.fondo_principal_animado, (ImageView) findViewById(R.id.fondoGif));

        //Cargar el número de la versión de la app.
        final TextView txtViewVersion = findViewById(R.id.txtViewVersion);
        txtViewVersion.append(" " + BuildConfig.VERSION_NAME);

        Button btnIntroduccion = findViewById(R.id.btnIntroduccion);
        Button btnCambiarIdioma = findViewById(R.id.btnCambiarIdioma);
        Button btnContactar = findViewById(R.id.btnContactar);
        Button btnCreditos = findViewById(R.id.btnCreditos);
        ImageButton btnVolver = findViewById(R.id.btnVolver);
        SwitchCompat switchEfecto = findViewById(R.id.switchEfecto);
        SwitchCompat switchMusica = findViewById(R.id.switchMusica);

        switchEfecto.setChecked(EffectSoundHelper.reproducirEfecto);

        switchEfecto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EffectSoundHelper.reproducirEfecto = isChecked;
            }
        });

        switchMusica.setChecked(reproducirMusica);

        switchMusica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                reproducirMusica = isChecked;

                if (!reproducirMusica) musicaFondo.setVolume(0f, 0f);
                else {
                    if (musicaFondo != null) musicaFondo.setVolume(0.5f, 0.5f);
                    else {
                        musicaFondo = MediaPlayer.create(AjustesActivity.this, id_musica);
                        musicaFondo.setVolume(0.5f, 0.5f);
                        musicaFondo.setLooping(true);
                        musicaFondo.start();
                    }
                }
            }
        });

        btnIntroduccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EffectSoundHelper.reproducirEfecto(AjustesActivity.this, R.raw.boton_click);

                IntroduccionActivity.activity_anterior = "ajustes";

                startActivity(new Intent(AjustesActivity.this, IntroduccionActivity.class));

                finish();
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EffectSoundHelper.reproducirEfecto(AjustesActivity.this, R.raw.boton_click);

                startActivity(new Intent(AjustesActivity.this, MenuActivity.class));

                finish();
            }
        });

        btnCambiarIdioma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EffectSoundHelper.reproducirEfecto(AjustesActivity.this, R.raw.boton_click);

                IdiomaActivity.activity_anterior = "ajustes";

                startActivity(new Intent(AjustesActivity.this, IdiomaActivity.class));

                finish();
            }
        });

        btnContactar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EffectSoundHelper.reproducirEfecto(AjustesActivity.this, R.raw.boton_click);

                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"ecoartscep@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "");
                email.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(email, ""));
            }
        });

        btnCreditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EffectSoundHelper.reproducirEfecto(AjustesActivity.this, R.raw.boton_click);

                startActivity(new Intent(AjustesActivity.this, CreditosActivity.class));

                finish();
            }
        });
    }
}
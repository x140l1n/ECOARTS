package com.example.edujoc_cepsoft;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edujoc_cepsoft.Helpers.EffectSoundHelper;
import com.example.edujoc_cepsoft.Helpers.GifHelper;

public class IntroduccionActivity extends MiActivityPersonalizado
{
    public static String activity_anterior = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduccion);

        GifHelper.loadGif(this, R.drawable.fondo_principal_animado, (ImageView) findViewById(R.id.fondoGif));

        id_musica = R.raw.menu;
        musicaFondo = null;

        TextView txtViewIntroduccion = findViewById(R.id.txtViewIntroduccion);

        //Esto es para poner el scroll bar.
        txtViewIntroduccion.setMovementMethod(new ScrollingMovementMethod());

        Button btnIr = findViewById(R.id.btnIr);

        if (activity_anterior.equals("ajustes")) btnIr.setText(getString(R.string.volver_ajustes));

        btnIr.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EffectSoundHelper.reproducirEfecto(IntroduccionActivity.this, R.raw.boton_click);

                if (activity_anterior.equals("idioma")) startActivity(new Intent(IntroduccionActivity.this, MenuActivity.class));
                else startActivity(new Intent(IntroduccionActivity.this, AjustesActivity.class));

                finish();
            }
        });
    }
}
package com.example.edujoc_cepsoft;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.edujoc_cepsoft.Helpers.EffectSoundHelper;
import com.example.edujoc_cepsoft.Helpers.GifHelper;

public class CreditosActivity extends MiActivityPersonalizado
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);

        GifHelper.loadGif(this, R.drawable.fondo_principal_animado, (ImageView) findViewById(R.id.fondoGif));

        final ImageButton btnVolver = findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EffectSoundHelper.reproducirEfecto(CreditosActivity.this, R.raw.boton_click);

                finish();
            }
        });
    }
}
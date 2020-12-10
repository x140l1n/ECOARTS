package com.example.edujoc_cepsoft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.edujoc_cepsoft.Helpers.GifHelper;
import com.example.edujoc_cepsoft.Helpers.LocaleHelper;

public class IdiomaActivity extends MiActivityPersonalizado
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idioma);

        GifHelper.loadGif(this, R.drawable.fondo_principal_animado, (ImageView) findViewById(R.id.fondoGif));

        final Button btnEs = findViewById(R.id.btnCastellano);
        final Button btnCa = findViewById(R.id.btnCatala);
        final Button btnEn = findViewById(R.id.btnEnglish);

        btnEs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LocaleHelper.setLocale(IdiomaActivity.this, "es");
                startActivity(new Intent(IdiomaActivity.this, MenuActivity.class));
                finish();
            }
        });

        btnCa.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LocaleHelper.setLocale(IdiomaActivity.this, "ca");
                startActivity(new Intent(IdiomaActivity.this, MenuActivity.class));
                finish();
            }
        });

        btnEn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LocaleHelper.setLocale(IdiomaActivity.this, "en");
                startActivity(new Intent(IdiomaActivity.this, MenuActivity.class));
                finish();
            }
        });
    }
}
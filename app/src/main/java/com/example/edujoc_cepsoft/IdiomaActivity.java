package com.example.edujoc_cepsoft;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.edujoc_cepsoft.Helpers.GifHelper;
import com.example.edujoc_cepsoft.Helpers.LocaleHelper;
import com.example.edujoc_cepsoft.Helpers.MediaPlayerHelper;

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

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }
}
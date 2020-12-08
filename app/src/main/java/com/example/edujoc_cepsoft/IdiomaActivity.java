package com.example.edujoc_cepsoft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.edujoc_cepsoft.Helpers.GifHelper;
import com.example.edujoc_cepsoft.Helpers.LocaleHelper;
import com.example.edujoc_cepsoft.Helpers.MediaPlayerHelper;

public class IdiomaActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idioma);

        GifHelper.loadGif(this, R.drawable.background_animation, (ImageView) findViewById(R.id.fondoGif));

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
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
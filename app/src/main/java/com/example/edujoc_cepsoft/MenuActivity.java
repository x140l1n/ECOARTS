package com.example.edujoc_cepsoft;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.edujoc_cepsoft.Helpers.GifHelper;

public class MenuActivity extends MiActivityPersonalizado
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        GifHelper.loadGif(this, R.drawable.fondo_principal_animado, (ImageView) findViewById(R.id.fondoGif));

        id_musica = R.raw.menu;
        musicaFondo = null;

        final Button btnJugar = findViewById(R.id.btnJugar);
        final Button btnAjustes = findViewById(R.id.btnAjustes);
        final Button btnSalir = findViewById(R.id.btnSalir);

        btnJugar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                reproducirEfecto(MenuActivity.this, R.raw.boton_click);

                startActivity(new Intent(MenuActivity.this, SelectPersonajeActivity.class));
            }
        });

        btnAjustes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                reproducirEfecto(MenuActivity.this, R.raw.boton_click);

                startActivity(new Intent(MenuActivity.this, AjustesActivity.class));
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                reproducirEfecto(MenuActivity.this, R.raw.boton_click);

                final Dialog dialogSalir = new MiDialogPersonalizado(MenuActivity.this, R.layout.dialog_salir);

                ImageButton btnSalir = dialogSalir.findViewById(R.id.btnSalir);
                ImageButton btnCancelar = dialogSalir.findViewById(R.id.btnCancelar);

                btnCancelar.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        reproducirEfecto(MenuActivity.this, R.raw.boton_click);

                        dialogSalir.dismiss();
                    }
                });

                btnSalir.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        reproducirEfecto(MenuActivity.this, R.raw.boton_click);

                        dialogSalir.dismiss();

                        id_musica = 0;
                        musicaFondo.stop();
                        musicaFondo = null;

                        finishAffinity();
                    }
                });

                dialogSalir.show();
            }
        });
    }
}
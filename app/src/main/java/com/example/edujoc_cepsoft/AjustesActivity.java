package com.example.edujoc_cepsoft;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edujoc_cepsoft.Data.PaginaTutorial;
import com.example.edujoc_cepsoft.Helpers.GifHelper;

import java.util.ArrayList;
import java.util.List;

public class AjustesActivity extends MiActivityPersonalizado
{
    private int pagina_tutorial = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        GifHelper.loadGif(this, R.drawable.fondo_principal_animado, (ImageView) findViewById(R.id.fondoGif));

        //Cargar el número de la versión de la app.
        final TextView txtViewVersion = findViewById(R.id.txtViewVersion);
        txtViewVersion.append(" " + BuildConfig.VERSION_NAME);

        final Button btnTutorial = findViewById(R.id.btnTutorial);
        final Button btnCambiarIdioma = findViewById(R.id.btnCambiarIdioma);
        final Button btnContactar = findViewById(R.id.btnContactar);
        final Button btnCreditos = findViewById(R.id.btnCreditos);
        final ImageButton btnVolver = findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        btnTutorial.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = new MiDialogPersonalizado(AjustesActivity.this, R.layout.dialog_tutorial);

                final List<PaginaTutorial> paginas = new ArrayList<>();
                paginas.add(new PaginaTutorial(R.drawable.ecoartslogo_sin_eslogan, getString(R.string.introduccuion_tutorial)));
                paginas.add(new PaginaTutorial(R.drawable.tutorial_1, getString(R.string.tutorial_1)));
                paginas.add(new PaginaTutorial(R.drawable.tutorial_2, getString(R.string.tutorial_2)));
                paginas.add(new PaginaTutorial(R.drawable.tutorial_3, getString(R.string.tutorial_3)));

                ImageButton btnCancelar = dialog.findViewById(R.id.btnCancelar);
                ImageButton btnAnterior = dialog.findViewById(R.id.btnAnterior);
                ImageButton btnSiguiente = dialog.findViewById(R.id.btnSiguiente);

                final ImageView imagenTutorial = dialog.findViewById(R.id.imagenTutorial);
                final TextView textViewTutorial = dialog.findViewById(R.id.textViewTutorial);

                //Primero mostramos la introducción.
                PaginaTutorial paginaTutorial = paginas.get(pagina_tutorial);

                imagenTutorial.setImageResource(paginaTutorial.getImagen());
                textViewTutorial.setText(paginaTutorial.getTexto());

                btnCancelar.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                    }
                });

                btnAnterior.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //Comprobamos la anterior página no sea menor que 0.
                        if (pagina_tutorial - 1 >= 0)
                        {
                            pagina_tutorial--;

                            PaginaTutorial paginaTutorial = paginas.get(pagina_tutorial);

                            imagenTutorial.setImageResource(paginaTutorial.getImagen());
                            textViewTutorial.setText(paginaTutorial.getTexto());
                        }
                    }
                });

                btnSiguiente.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //Comprobamos la siguiente página no sea mayor que el número de paginas.
                        if (pagina_tutorial + 1 < paginas.size())
                        {
                            pagina_tutorial++;

                            PaginaTutorial paginaTutorial = paginas.get(pagina_tutorial);

                            imagenTutorial.setImageResource(paginaTutorial.getImagen());
                            textViewTutorial.setText(paginaTutorial.getTexto());
                        }
                    }
                });

                dialog.show();
            }
        });

        btnCambiarIdioma.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AjustesActivity.this, IdiomaActivity.class));
            }
        });

        btnContactar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                email.putExtra(Intent.EXTRA_EMAIL, new String[] { "ecoartscep@gmail.com" });
                email.putExtra(Intent.EXTRA_SUBJECT, "");
                email.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(email, ""));
            }
        });

        btnCreditos.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AjustesActivity.this, CreditosActivity.class));
            }
        });
    }
}
package com.example.edujoc_cepsoft;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.edujoc_cepsoft.Adapters.SelectPersonajeAdapter;
import com.example.edujoc_cepsoft.Data.Personaje;
import com.example.edujoc_cepsoft.Helpers.GifHelper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class SelectPersonajeActivity extends MiActivityPersonalizado
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_personaje);

        GifHelper.loadGif(this, R.drawable.fondo_principal_animado, (ImageView) findViewById(R.id.fondoGif));

        GridView gridViewPersonajes = findViewById(R.id.gridViewPersonajes);

        ArrayList<Personaje> personajes = cargarPersonajes();

        if (personajes != null) gridViewPersonajes.setAdapter(new SelectPersonajeAdapter(this, personajes));
        else System.err.println("No se ha podido cargar los personajes.");

        final EditText editTextNombreJugador = findViewById(R.id.editTextNombreJugador);
        final Button btnEmpezar = findViewById(R.id.btnEmpezar);
        final ImageButton btnVolver = findViewById(R.id.btnVolver);

        //Cuando el edit text cambia el texto.
        editTextNombreJugador.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                //Si está vació el texto, es decir, count == 0, deshabilitamos el botón empezar, al contrario, habilitamos.
                if (count == 0) btnEmpezar.setEnabled(false);
                else btnEmpezar.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnEmpezar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Probar...
                Toast.makeText(SelectPersonajeActivity.this, "Comenzar el juego", Toast.LENGTH_SHORT).show();
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    /**
     * Cargar los personajes desde el fichero .json.
     * @return La lista de personajes, devuelve null si no se ha podido cargar.
     */
    @SuppressWarnings("deprecation") //Quitar la advertencia de usar métodos en desuso.
    private ArrayList<Personaje> cargarPersonajes()
    {
        ArrayList<Personaje> personajes = null;

        Locale lang = this.getResources().getConfiguration().locale;

        String rutaFicheroJson = this.getFilesDir() + File.separator + "personajes" + File.separator;

        switch (lang.getLanguage())
        {
            case "es": rutaFicheroJson += "personajes_es.json"; break;
            case "ca": rutaFicheroJson += "personajes_ca.json"; break;
            case "en": rutaFicheroJson += "personajes_en.json"; break;
            default:   rutaFicheroJson  = null;                 break;
        }

        if (rutaFicheroJson != null)
        {
            BufferedReader br = null;
            String[] files = this.fileList();
            for(String item : files){
                System.out.println("ficheros: " + item);
            }
            try
            {
                br = new BufferedReader(new FileReader(rutaFicheroJson));

                personajes = new ArrayList<>(Arrays.asList(new Gson().fromJson(br, Personaje[].class)));

                if (personajes.size() != 0) personajes.get(0).setSeleccionado(true); //Seleccionamos por defecto el primer personaje.
            }
            catch (FileNotFoundException ex)
            {
                System.err.println("Fichero json no encontrado:\n" + ex.getMessage());
            }
            finally //Si ha saltado un error o no, cerraremos igualmente el BufferedReader.
            {
                try
                {
                    if (br != null) br.close();
                }
                catch (IOException ex)
                {
                    System.err.println("Error al cerrar el Buffered Reader:\n" + ex.getMessage());
                }
            }
        }
        else
        {
            System.err.println("No se ha podiddo obtener la ruta del fichero json. Idioma detectado: " + lang.getLanguage());
        }

        return personajes;
    }
}
package com.example.edujoc_cepsoft;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edujoc_cepsoft.Adapters.SelectPersonajeAdapter;
import com.example.edujoc_cepsoft.Data.Personaje;
import com.example.edujoc_cepsoft.Helpers.GifHelper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class SelectPersonajeActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_personaje);

        GifHelper.loadGif(this, R.drawable.background_animation, (ImageView) findViewById(R.id.fondoGif));

        GridView gridViewPersonajes = findViewById(R.id.gridViewPersonajes);

        ArrayList<Personaje> personajes = cargarPersonajes();

        if (personajes != null) gridViewPersonajes.setAdapter(new SelectPersonajeAdapter(this, personajes));
        else System.err.println("No se ha podido cargar los personajes.");

        final ImageButton btnVolver = findViewById(R.id.btnVolver);

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
            default:   rutaFicheroJson = null; break;
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
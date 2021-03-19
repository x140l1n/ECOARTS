package com.example.edujoc_cepsoft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.edujoc_cepsoft.Adapters.SelectPersonajeAdapter;
import com.example.edujoc_cepsoft.Data.Personaje;
import com.example.edujoc_cepsoft.Helpers.EffectSoundHelper;
import com.example.edujoc_cepsoft.Helpers.GifHelper;
import com.example.edujoc_cepsoft.Helpers.SystemUIHelper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class SelectPersonajeActivity extends MiActivityPersonalizado {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_personaje);

        GifHelper.loadGif(this, R.drawable.fondo_principal_animado, (ImageView) findViewById(R.id.fondoGif));

        GridView gridViewPersonajes = findViewById(R.id.gridViewPersonajes);

        final ArrayList<Personaje> personajes = cargarPersonajes();

        if (personajes != null) gridViewPersonajes.setAdapter(new SelectPersonajeAdapter(this, personajes));
        else System.err.println("No se ha podido cargar los personajes.");

        final EditText editTextNombreJugador = findViewById(R.id.editTextNombreJugador);
        final Button btnEmpezar = findViewById(R.id.btnEmpezar);
        final ImageButton btnVolver = findViewById(R.id.btnVolver);
        final RelativeLayout relativeLayoutSelectPersonaje = findViewById(R.id.relativeLayoutSelectPersonaje);

        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EffectSoundHelper.reproducirEfecto(SelectPersonajeActivity.this, R.raw.boton_click);

                if (personajes.size() > 0 && !editTextNombreJugador.getText().toString().trim().isEmpty()) {
                    Personaje personajeSeleccionado;
                    int index = 0;

                    do {
                        personajeSeleccionado = personajes.get(index);

                        index++;
                    }
                    while (!personajeSeleccionado.isSeleccionado());

                    Intent intent = new Intent(SelectPersonajeActivity.this, MapaActivity.class);
                    intent.putExtra(MapaActivity.PERSONAJE, personajeSeleccionado);
                    String nivel;

                    RadioButton rbFacil = findViewById(R.id.radioButtonFacil);

                    if (rbFacil.isChecked()) nivel = "facil";
                    else nivel = "dificil";

                    intent.putExtra(MapaActivity.NIVEL, nivel);
                    intent.putExtra(MapaActivity.NOMBRE_JUGADOR, editTextNombreJugador.getText().toString().trim());
                    startActivity(intent);
                    finish();
                } else {
                    if (editTextNombreJugador.getText().toString().trim().isEmpty()) {
                        Toast.makeText(SelectPersonajeActivity.this, R.string.debes_escribir_nombre, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //Para ocultar la barra de navegación y el teclado cuando haces click en el layout.
        relativeLayoutSelectPersonaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCurrentFocus() != null && editTextNombreJugador.hasFocus()) {
                    //Ocultar el teclado.
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                    SystemUIHelper.ocultarBarraNavegacion(SelectPersonajeActivity.this.getWindow());

                    editTextNombreJugador.clearFocus();
                }
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EffectSoundHelper.reproducirEfecto(SelectPersonajeActivity.this, R.raw.boton_click);

                startActivity(new Intent(SelectPersonajeActivity.this, MenuActivity.class));

                finish();
            }
        });
    }

    /**
     * Cargar los personajes desde el fichero .json.
     *
     * @return La lista de personajes, devuelve null si no se ha podido cargar.
     */
    @SuppressWarnings("deprecation") //Quitar la advertencia de usar métodos en desuso.
    private ArrayList<Personaje> cargarPersonajes() {
        ArrayList<Personaje> personajes = null;

        Locale lang = this.getResources().getConfiguration().locale;

        String rutaFicheroJson = this.getFilesDir() + File.separator + "personajes" + File.separator;

        switch (lang.getLanguage()) {
            case "es":
                rutaFicheroJson += "personajes_es.json";
                break;
            case "ca":
                rutaFicheroJson += "personajes_ca.json";
                break;
            case "en":
                rutaFicheroJson += "personajes_en.json";
                break;
            default:
                rutaFicheroJson = null;
                break;
        }

        if (rutaFicheroJson != null) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(rutaFicheroJson));

                personajes = new ArrayList<>(Arrays.asList(new Gson().fromJson(br, Personaje[].class)));

                if (personajes.size() != 0)
                    personajes.get(0).setSeleccionado(true); //Seleccionamos por defecto el primer personaje.
            } catch (FileNotFoundException ex) {
                System.err.println("Fichero json no encontrado:\n" + ex.getMessage());
            } finally //Si ha saltado un error o no, cerraremos igualmente el BufferedReader.
            {
                try {
                    if (br != null) br.close();
                } catch (IOException ex) {
                    System.err.println("Error al cerrar el Buffered Reader:\n" + ex.getMessage());
                }
            }
        } else {
            System.err.println("No se ha podiddo obtener la ruta del fichero json. Idioma detectado: " + lang.getLanguage());
        }

        return personajes;
    }
}
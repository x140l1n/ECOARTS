package com.example.edujoc_cepsoft;

import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.edujoc_cepsoft.Helpers.SystemUIHelper;

/**
 * Clase Activity personalizada. Creamos la clase para que en todas las Activities que se extienda de esta clase se oculte la barra de navegación y reproducir efectos de sonido y música.
 */
public class MiActivityPersonalizado extends AppCompatActivity {
    protected static int id_musica = 0;
    protected static MediaPlayer musicaFondo = null;
    protected static boolean sonarMusica = true;

    /**
     * Cuando el activity pasa al primer plano.
     */
    @Override
    protected void onResume() {
        SystemUIHelper.ocultarBarraNavegacion(this.getWindow());

        super.onResume();

        if (musicaFondo != null && !musicaFondo.isPlaying() && sonarMusica) {
            musicaFondo.start();
        }
    }

    /**
     * Cuando el activity se para.
     */
    @Override
    protected void onPause() {
        super.onPause();

        if (musicaFondo != null && musicaFondo.isPlaying()) {
            musicaFondo.pause();
        }
    }

    /**
     * Cuando la ventana del activity cambia el foco.
     *
     * @param hasFocus Si la ventana tiene el foco.
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        SystemUIHelper.ocultarBarraNavegacion(this.getWindow());
    }

    @Override
    public void onBackPressed() {
        //No hacer nada cuando el jugador hace click en el botón back del dispositivo.
    }
}

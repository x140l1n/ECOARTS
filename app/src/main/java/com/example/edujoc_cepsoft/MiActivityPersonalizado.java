package com.example.edujoc_cepsoft;

import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.edujoc_cepsoft.Helpers.SystemUIHelper;

/**
 * Clase Activity personalizada. Creamos la clase para que en todas las Activities que se extienda de esta clase se oculte la barra de navegación y reproducir efectos de sonido y música.
 */
public class MiActivityPersonalizado extends AppCompatActivity {
    private static int posicion = 0;

    protected static int id_musica = 0;
    protected MediaPlayer musicaFondo = null;
    protected static boolean reproducirMusica = true;

    @Override
    public void onBackPressed() {
        //No hacer nada cuando el jugador hace click en el botón back del dispositivo.
    }

    /**
     * Cuando el activity pasa al primer plano.
     */
    @Override
    protected void onResume() {
        SystemUIHelper.ocultarBarraNavegacion(this.getWindow());

        if (id_musica != 0 && musicaFondo == null && reproducirMusica) {
            musicaFondo = MediaPlayer.create(this, id_musica);
            musicaFondo.setVolume(0.5f, 0.5f);
            musicaFondo.setLooping(true);
            musicaFondo.start();
        }

        super.onResume();
    }

    /**
     * Cuando la ventana del activity cambia el foco.
     *
     * @param hasFocus Si la ventana tiene el foco.
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        SystemUIHelper.ocultarBarraNavegacion(this.getWindow());

        super.onWindowFocusChanged(hasFocus);
    }

    /**
     * Cuando el activity se reinicia.
     */
    @Override
    protected void onRestart() {
        if (musicaFondo != null && reproducirMusica) {
            musicaFondo.seekTo(posicion);
            musicaFondo.start();
        }

        super.onRestart();
    }

    /**
     * Cuando el activity se para.
     */
    @Override
    protected void onStop() {
        if (musicaFondo != null) {
            musicaFondo.stop();
            posicion = musicaFondo.getCurrentPosition();
        }

        super.onStop();
    }

    /**
     * Cuando el activity se pausa.
     */
    @Override
    protected void onPause() {
        if (musicaFondo != null && musicaFondo.isPlaying()) {
            musicaFondo.release();
            musicaFondo = null;
        }

        super.onPause();
    }
}

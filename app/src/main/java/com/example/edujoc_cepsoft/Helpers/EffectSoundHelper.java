package com.example.edujoc_cepsoft.Helpers;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Clase para cambiar crear un efecto de sonido. Creamos como clase para que en cualquier activity podamos llamar a esta clase para crear un efecto de sonido.
 */
public class EffectSoundHelper {
    public static boolean reproducirEfecto = true;

    /**
     * Reproducir un efecto de sonido. Si la variable reproducir es true se va a reproducir el efecto, en caso contrario, no se va a reproducir.
     *
     * @param context   El contexto del activity en que se reproduce el efecto de sonido.
     * @param id_efecto El id del efecto que queremos reproducir.
     */
    public static void reproducirEfecto(Context context, int id_efecto) {
        if (reproducirEfecto) {
            MediaPlayer efecto = MediaPlayer.create(context, id_efecto);
            efecto.setVolume(1f, 1f);
            efecto.start();
        }
    }
}

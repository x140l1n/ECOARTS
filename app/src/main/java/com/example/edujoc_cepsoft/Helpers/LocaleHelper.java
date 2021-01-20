package com.example.edujoc_cepsoft.Helpers;

import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import java.util.Locale;

/**
 * Clase para cambiar el idioma. Creamos como clase para que en cualquier activity podamos llamar a esta clase para cambiar el idioma.
 */
public class LocaleHelper {
    /**
     * Cambiar el idioma de la app.
     *
     * @param context El contexto del activity en que se cambia el idioma.
     * @param lang    El idioma a que queremos cambiar.
     */
    @SuppressWarnings("deprecation") //Quitar la advertencia de usar métodos en desuso.
    public static void setLocale(@NonNull Context context, String lang) {
        //Creamos un objeto de tipo Configuration pasando por parámetro la configuración que tiene el context.
        //Esta clase describe toda la información de configuración del dispositivo que puede afectar los recursos que recupera la aplicación.
        //Esto incluye tanto las opciones de configuración especificadas por el usuario (lista de configuraciones regionales y escala) como las
        //configuraciones del dispositivo (como los modos de entrada, el tamaño de la pantalla y la orientación de la pantalla).
        Configuration config = new Configuration(context.getResources().getConfiguration());

        //Insertamos el locale pasando un objeto tipo Locale.
        //En el locale pasamos la variable lang por constructor donde contiene el idioma, ca, es, en, etc...
        config.setLocale(new Locale(lang));

        //Actualizar la configuración del resource del context, pasando por paramétro el config y el display metrics.
        //El display metrics es una estructura que describe información general sobre una pantalla, como su tamaño, densidad y escala de fuente.
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }
}

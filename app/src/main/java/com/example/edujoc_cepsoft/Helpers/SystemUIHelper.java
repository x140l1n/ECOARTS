package com.example.edujoc_cepsoft.Helpers;

import android.os.Build;
import android.view.View;
import android.view.Window;

/**
 * Clase para manipular el interfaz de usuario.
 */
public class SystemUIHelper {
    /**
     * Para ocultar la barra de navegaciñon de cualquier dispositivo.
     * Según la versión del SDK usaremos un método para ocultar o otro.
     *
     * @param window La ventana en la que se va a ocultar la barra de navegación.
     */
    @SuppressWarnings("deprecation") //Quitar la advertencia de usar métodos en desuso.
    public static void ocultarBarraNavegacion(Window window) {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = window.getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            int opciones = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            window.getDecorView().setSystemUiVisibility(opciones);
        }
    }
}

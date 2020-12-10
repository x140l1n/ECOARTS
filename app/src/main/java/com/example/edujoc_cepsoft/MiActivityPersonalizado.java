package com.example.edujoc_cepsoft;

import androidx.appcompat.app.AppCompatActivity;

import com.example.edujoc_cepsoft.Helpers.SystemUIHelper;

/**
 * Clase Activity personalizada. Creamos la clase para que en todas las Activities que se extienda de esta clase se oculte la barra de navegación.
 */
public class MiActivityPersonalizado extends AppCompatActivity
{
    /**
     * Cuando el activity pasa al primer plano.
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        SystemUIHelper.ocultarBarraNavegacion(this.getWindow());
    }

    /**
     * Cuando la ventana del activity cambia el foco.
     * @param hasFocus Si la ventana tiene el foco.
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        SystemUIHelper.ocultarBarraNavegacion(this.getWindow());
    }
}

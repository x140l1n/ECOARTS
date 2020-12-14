package com.example.edujoc_cepsoft.Helpers;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.edujoc_cepsoft.R;

import org.jetbrains.annotations.NotNull;

/**
 * Clase para cambiar cargar un gif. Creamos como clase para que en cualquier activity podamos llamar a esta clase para cargar un gif.
 */
public class GifHelper
{
    /**
     * Cargar un gif como fondo del activity.
     * @param context El contexto del activity en que se inserta el gif.
     * @param drawableGif  El id del gif que vamos a cargar al image view.
     * @param imgViewFondo El imageview que va a recibir el gif.
     */
    public static void loadGif(@NotNull Context context, int drawableGif, ImageView imgViewFondo)
    {
        Glide.with(context.getApplicationContext()).load(drawableGif).placeholder(R.drawable.fondo_principal).dontAnimate().into(imgViewFondo);
    }
}

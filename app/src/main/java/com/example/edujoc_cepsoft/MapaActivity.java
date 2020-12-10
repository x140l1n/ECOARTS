package com.example.edujoc_cepsoft;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.edujoc_cepsoft.Data.Personaje;

import java.io.File;

public class MapaActivity extends MiActivityPersonalizado
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        Personaje personaje = new Personaje(3, "Trump", "es", ".//personajes//img//trump_es_3.png","sdshdhsdjhsjd");
        ImageView personajeMapa = findViewById(R.id.personajeMapa);

        //String rutaAbsolutaPersonaje = personaje.getRutaImagen();
        //Bitmap bitmapPersonaje = BitmapFactory.decodeFile(rutaAbsolutaPersonaje);
        //personajeMapa.setImageBitmap(bitmapPersonaje);

        View view = findViewById(R.id.personaje);

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator oa = ObjectAnimator.ofFloat(view, "translationX", 375.50f);
        oa.setDuration(2000);
        ObjectAnimator oa1 = ObjectAnimator.ofFloat(view, "translationY", -170f);
        oa1.setDuration(2000);
        ObjectAnimator oa2 = ObjectAnimator.ofFloat(view, "translationX",  (this.getResources().getDisplayMetrics().heightPixels - 1300));
        oa2.setDuration(2000);
        animatorSet.playSequentially(oa, oa1, oa2);
        animatorSet.start();


    }
    /*Crear funcio de startActivityForResult


        AnimatorSet animatorSet = new AnimatorSet();
        ;
        ObjectAnimator oa = ObjectAnimator.ofFloat(view, "translationX", 375.50f);
        oa.setDuration(2000);
        ObjectAnimator oa1 = ObjectAnimator.ofFloat(view, "translationY", -170f);
        oa1.setDuration(2000);
        ObjectAnimator oa2 = ObjectAnimator.ofFloat(view, "translationX",  (this.getResources().getDisplayMetrics().heightPixels - 1300));
        oa2.setDuration(2000);
        animatorSet.playSequentially(oa, oa1, oa2);
        animatorSet.start();
     */
}
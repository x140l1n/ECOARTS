package com.example.edujoc_cepsoft;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MapaActivity extends MiActivityPersonalizado
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        //Personaje personaje = new Personaje(3, "Trump", "es", ".//personajes//img//trump_es_3.png","sdshdhsdjhsjd");
        //ImageView personajeMapa = findViewById(R.id.personajeMapa);

        //String rutaAbsolutaPersonaje = personaje.getRutaImagen();
        //Bitmap bitmapPersonaje = BitmapFactory.decodeFile(rutaAbsolutaPersonaje);
        //personajeMapa.setImageBitmap(bitmapPersonaje);

        final View personaje = findViewById(R.id.personaje);

        final Button btnJugar = personaje.findViewById(R.id.btnJugar);

        AnimatorSet as = new AnimatorSet();

        as.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                btnJugar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                btnJugar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                System.err.println("Cancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                System.err.println("Start");
            }
        });

        ObjectAnimator oa1 = ObjectAnimator.ofFloat(personaje, View.TRANSLATION_X, personaje.getX() + 350f);
        ObjectAnimator oa2 = ObjectAnimator.ofFloat(personaje, View.TRANSLATION_Y, personaje.getY() - 150f);
        ObjectAnimator oa3 = ObjectAnimator.ofFloat(personaje, View.TRANSLATION_X, personaje.getX() + 540f);
        ObjectAnimator oa4 = ObjectAnimator.ofFloat(personaje, View.TRANSLATION_Y, personaje.getY() - 320f);
        ObjectAnimator oa5 = ObjectAnimator.ofFloat(personaje, View.TRANSLATION_X, personaje.getX() + 85f);
        ObjectAnimator oa6 = ObjectAnimator.ofFloat(personaje, View.TRANSLATION_Y, personaje.getY() - 420f);

        as.playSequentially(oa1, oa2, oa3, oa4, oa5, oa6);
        as.setDuration(1200);
        as.start();
    }
}
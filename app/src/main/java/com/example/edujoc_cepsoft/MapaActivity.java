package com.example.edujoc_cepsoft;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.edujoc_cepsoft.Helpers.GifHelper;

public class MapaActivity extends MiActivityPersonalizado
{
    private Button btnJugar;
    private ImageButton btnConfig;
    private View personaje;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        GifHelper.loadGif(this, R.drawable.fondo_principal_animado, (ImageView) findViewById(R.id.fondoGif));

        personaje   = findViewById(R.id.personaje);
        btnConfig   = findViewById(R.id.btnConfig);
        btnJugar    = personaje.findViewById(R.id.btnJugar);

        btnJugar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                
            }
        });

        btnConfig.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog dialogAjuste = new MiDialogPersonalizado(MapaActivity.this, R.layout.dialog_ajuste_partida);
                dialogAjuste.show();
                ImageButton btnVolverPartida = dialogAjuste.findViewById(R.id.btnVolverPartida);
                btnVolverPartida.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogAjuste.dismiss();
                    }
                });
            }
        });

    }

    /**
     * Realizar la primera animación, del primer mundo al segundo mundo.
     */
    private void animacion1()
    {
        final Button btnJugar = personaje.findViewById(R.id.btnJugar);

        AnimatorSet as = new AnimatorSet();
        as.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation)
            {
                btnJugar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                btnJugar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
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
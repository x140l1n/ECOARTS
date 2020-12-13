package com.example.edujoc_cepsoft;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.edujoc_cepsoft.Data.Enemigo;
import com.example.edujoc_cepsoft.Data.Personaje;
import com.example.edujoc_cepsoft.Helpers.GifHelper;

import java.util.ArrayList;

public class MapaActivity extends MiActivityPersonalizado
{
    public static String PERSONAJE = "nombre";
    public final static int RETORNO_BATALLA = 1;
    public static String NIVEL = "nivel";
    private Button btnJugar;
    private ImageButton btnConfig;
    private View personaje;
    private String nivel;
    private int batalla = 0;
    private ArrayList<Enemigo> enemigos = new ArrayList<Enemigo>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        GifHelper.loadGif(this, R.drawable.fondo_principal_animado, (ImageView) findViewById(R.id.fondoGif));
        Intent intent = getIntent();
        final Personaje personajeIntent = (Personaje)intent.getSerializableExtra(PERSONAJE);
        nivel = intent.getStringExtra(NIVEL);
        personaje   = findViewById(R.id.personaje);
        btnConfig   = findViewById(R.id.btnConfig);
        btnJugar    = personaje.findViewById(R.id.btnJugar);
        cargarEnemigos(enemigos);

        btnJugar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MapaActivity.this, BatallaActivity.class);
                intent.putExtra(BatallaActivity.NOMBRE_ENEMIGO, enemigos.get(batalla));
                intent.putExtra(BatallaActivity.NOMBRE_PERSONAJE, personajeIntent);
                intent.putExtra(BatallaActivity.NUMERO_BATALLA, batalla);
                if(batalla<6){
                    startActivityForResult(intent, RETORNO_BATALLA);
                }else{
                    startActivity(intent);
                }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RETORNO_BATALLA:
                Personaje personaje = (Personaje) data.getSerializableExtra(PERSONAJE);
                if(nivel.equals("facil")){
                    personaje.setVidas(5);
                }
                batalla++;
                break;

        }
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

    private void cargarEnemigos(ArrayList<Enemigo> enemigos){
        Enemigo enemigo1 = new Enemigo(R.drawable.enemigo_agua, "Vaporeon");
        Enemigo enemigo2 = new Enemigo(R.drawable.enemigo_bosques, "MaquinaBosque");
        Enemigo enemigo3 = new Enemigo(R.drawable.enemigo_energia, "Energia");
        Enemigo enemigo4 = new Enemigo(R.drawable.enemigo_gas, "Gas");
        Enemigo enemigo5 = new Enemigo(R.drawable.enemigo_plastico, "Plástico");
        Enemigo enemigo6 = new Enemigo(R.drawable.enemigo_residuos, "Residuos");

        enemigos.add(enemigo1);
        enemigos.add(enemigo2);
        enemigos.add(enemigo3);
        enemigos.add(enemigo4);
        enemigos.add(enemigo5);
        enemigos.add(enemigo6);
    }
}
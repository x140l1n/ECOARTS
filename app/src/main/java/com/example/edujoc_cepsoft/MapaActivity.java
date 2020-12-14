package com.example.edujoc_cepsoft;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.edujoc_cepsoft.Data.Enemigo;
import com.example.edujoc_cepsoft.Data.Personaje;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MapaActivity extends MiActivityPersonalizado
{
    public static final String PERSONAJE = "personaje";
    public static final String NOMBRE_JUGADOR = "nombre_jugador";
    public static final String NIVEL = "nivel";

    private Button btnJugar;
    private ImageButton btnConfig;
    private View viewPersonaje;
    private String nivel;

    private int numBatalla = 1;

    private ArrayList<Enemigo> enemigos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        Intent intent = getIntent();

        final Personaje personaje = (Personaje)intent.getSerializableExtra(PERSONAJE);
        String nombre = intent.getStringExtra(NOMBRE_JUGADOR);

        nivel           = intent.getStringExtra(NIVEL);
        viewPersonaje   = findViewById(R.id.personaje);
        btnConfig       = findViewById(R.id.btnConfig);
        btnJugar        = viewPersonaje.findViewById(R.id.btnJugar);

        final TextView nombreJugador = viewPersonaje.findViewById(R.id.nombreJugador);
        ImageView personajeMapa = viewPersonaje.findViewById(R.id.personajeMapa);

        nombreJugador.setText(nombre);
        personajeMapa.setImageBitmap(personaje.obtenerImagen(this));

        enemigos = cargarEnemigos();

        btnJugar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MapaActivity.this, BatallaActivity.class);

                intent.putExtra(BatallaActivity.ENEMIGO, enemigos.get(numBatalla - 1));
                intent.putExtra(BatallaActivity.PERSONAJE, personaje);
                intent.putExtra(BatallaActivity.NUMERO_BATALLA, numBatalla);
                intent.putExtra(BatallaActivity.JUGADOR, nombreJugador.getText());

                startActivityForResult(intent, BatallaActivity.BATALLA_ACTIVITY);
            }
        });

        btnConfig.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog dialogAjuste = new MiDialogPersonalizado(MapaActivity.this, R.layout.dialog_ajuste_partida);
                dialogAjuste.show();

                Button btnAbandonarPartida = dialogAjuste.findViewById(R.id.btnAbandonarPartida);
                ImageButton btnCancelar = dialogAjuste.findViewById(R.id.btnCancelar);

                btnAbandonarPartida.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        final Dialog dialogAbandonar = new MiDialogPersonalizado(MapaActivity.this, R.layout.dialog_abandonar_partida);
                        dialogAbandonar.show();

                        ImageButton btnAbandonar = dialogAbandonar.findViewById(R.id.btnAbandonar);
                        ImageButton btnCancelar = dialogAbandonar.findViewById(R.id.btnCancelar);

                        btnAbandonar.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                startActivity(new Intent(MapaActivity.this, MenuActivity.class));
                                finish();
                            }
                        });

                        btnCancelar.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                dialogAbandonar.dismiss();
                            }
                        });
                    }
                });

                btnCancelar.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialogAjuste.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        //No hacer nada cuando el jugador hace click en el botón back.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case BatallaActivity.BATALLA_ACTIVITY:
                if (resultCode == RESULT_OK)
                {
                    Personaje personaje = (Personaje) data.getSerializableExtra(PERSONAJE);

                    if (nivel.equals("facil")) personaje.setVidas(personaje.getVIDA_MAXIMA()); //Restablecer las vidas.

                    numBatalla++;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            realizarAnimacion();
                        }
                    }, 1000);
                }

                break;
        }
    }

    /**
     * Realizar la primera animación dependiendo en el número de la batalla actual.
     */
    private void realizarAnimacion()
    {
        final Button btnJugar = viewPersonaje.findViewById(R.id.btnJugar);

        AnimatorSet as = new AnimatorSet();
        as.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                btnJugar.setVisibility(View.INVISIBLE);
                btnJugar.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                btnJugar.setVisibility(View.VISIBLE);
                btnJugar.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {}

            @Override
            public void onAnimationRepeat(Animator animation)
            {}
        });

        switch (numBatalla)
        {
            case 1: break; //La primera batalla no hacemos ningúna animación.
            case 2:
                ObjectAnimator oa1 = ObjectAnimator.ofFloat(viewPersonaje, View.TRANSLATION_X, 350f);
                ObjectAnimator oa2 = ObjectAnimator.ofFloat(viewPersonaje, View.TRANSLATION_Y, -150f);
                ObjectAnimator oa3 = ObjectAnimator.ofFloat(viewPersonaje, View.TRANSLATION_X, 530f);

                as.playSequentially(oa1, oa2, oa3);

                break;
            case 3: break;
            case 4: break;
            case 5: break;
            case 6: break;
        }

        as.setDuration(1200);
        as.start();
    }

    @NotNull
    private ArrayList<Enemigo> cargarEnemigos()
    {
        ArrayList<Enemigo> enemigos = new ArrayList<Enemigo>();

        enemigos.add(new Enemigo(R.drawable.enemigo_agua, "Vaporeon"));
        enemigos.add(new Enemigo(R.drawable.enemigo_bosques, "MaquinaBosque"));
        enemigos.add(new Enemigo(R.drawable.enemigo_energia, "Energia"));
        enemigos.add(new Enemigo(R.drawable.enemigo_gas, "Gas"));
        enemigos.add(new Enemigo(R.drawable.enemigo_plastico, "Plástico"));
        enemigos.add(new Enemigo(R.drawable.enemigo_residuos, "Residuos"));

        return enemigos;
    }
}
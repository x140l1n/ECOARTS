package com.example.edujoc_cepsoft;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.paris.Paris;
import com.example.edujoc_cepsoft.Data.Enemigo;
import com.example.edujoc_cepsoft.Data.Personaje;
import com.example.edujoc_cepsoft.Data.Pregunta;
import com.example.edujoc_cepsoft.Data.Respuesta;
import com.example.edujoc_cepsoft.Helpers.GifHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class BatallaActivity extends MiActivityPersonalizado
{
    public final static int BATALLA_ACTIVITY = 1;

    private final long TIEMPO_MAXIMO = 30000; //Máximo 30 segundos.

    private final int DELAY_SIGUIENTE_PREGUNTA = 2000; //2 segundos de espera entre preguntas.

    public static final String ENEMIGO = "enemigo";
    public static final String PERSONAJE = "personaje";
    public static final String JUGADOR = "jugador";
    public static final String NUMERO_BATALLA = "batalla";

    private ProgressBar barra_progreso;
    private ObjectAnimator barra_progreso_animacion;

    private Button btnRespuesta1, btnRespuesta2, btnRespuesta3;

    private TextView textViewPregunta;

    private ArrayList<Pregunta> preguntas;
    private Pregunta pregunta;

    private LinearLayout vidasPersonaje, vidasEnemigo;

    private Enemigo enemigo;
    private Personaje personaje;

    private boolean preguntaRespondida = false;

    private String jugador;

    private int numeroBatalla;

    private ImageView batallaImagenPersonaje, batallaImagenEnemigo;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batalla);

        GifHelper.loadGif(this, R.drawable.fondo_principal_animado, (ImageView) findViewById(R.id.fondoGif));

        Intent intent = getIntent();

        personaje           = (Personaje) intent.getSerializableExtra(PERSONAJE);
        jugador             = intent.getStringExtra(JUGADOR);
        enemigo             = (Enemigo) intent.getSerializableExtra(ENEMIGO);
        numeroBatalla       = intent.getIntExtra(NUMERO_BATALLA, -1);

        barra_progreso      = findViewById(R.id.progress_bar);
        textViewPregunta    = findViewById(R.id.textViewPregunta);
        btnRespuesta1       = findViewById(R.id.btnRespuesta1);
        btnRespuesta2       = findViewById(R.id.btnRespuesta2);
        btnRespuesta3       = findViewById(R.id.btnRespuesta3);

        TextView batalla = findViewById(R.id.batalla);

        TextView batallaNombreJugador = findViewById(R.id.batallaNombreJugador);
        TextView batallaNombreEnemigo = findViewById(R.id.batallaNombreEnemigo);

        batallaImagenPersonaje = findViewById(R.id.batallaImagenPersonaje);
        batallaImagenEnemigo = findViewById(R.id.batallaImagenEnemigo);

        batalla.append(" " + numeroBatalla);

        batallaNombreJugador.setText(jugador);
        batallaNombreEnemigo.setText(enemigo.getNombre());

        batallaImagenPersonaje.setImageBitmap(personaje.obtenerImagen(this));
        batallaImagenEnemigo.setImageResource(enemigo.getImagen());

        cargarVidas(personaje, enemigo);

        btnRespuesta1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (btnRespuesta1.isEnabled() && btnRespuesta2.isEnabled() && btnRespuesta3.isEnabled())
                {
                    btnRespuesta1.setEnabled(false);
                    btnRespuesta2.setEnabled(false);
                    btnRespuesta3.setEnabled(false);

                    preguntaRespondida = true;

                    procesarRespuesta(pregunta.getRespuestas().get(0));

                    //Crea un nuevo hilo y se ejecutará pasadas 2000 milisegundos (2 segundos).
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            procesarResultado();
                        }
                    }, DELAY_SIGUIENTE_PREGUNTA);

                    preguntaRespondida = false;
                }
            }
        });

        btnRespuesta2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (btnRespuesta1.isEnabled() && btnRespuesta2.isEnabled() && btnRespuesta3.isEnabled())
                {
                    btnRespuesta1.setEnabled(false);
                    btnRespuesta2.setEnabled(false);
                    btnRespuesta3.setEnabled(false);

                    preguntaRespondida = true;

                    procesarRespuesta(pregunta.getRespuestas().get(1));

                    //Crea un nuevo hilo y se ejecutará pasadas 2000 milisegundos (2 segundos).
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            procesarResultado();
                        }
                    }, DELAY_SIGUIENTE_PREGUNTA);


                    preguntaRespondida = false;
                }
            }
        });

        btnRespuesta3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (btnRespuesta1.isEnabled() && btnRespuesta2.isEnabled() && btnRespuesta3.isEnabled())
                {
                    btnRespuesta1.setEnabled(false);
                    btnRespuesta2.setEnabled(false);
                    btnRespuesta3.setEnabled(false);

                    preguntaRespondida = true;

                    procesarRespuesta(pregunta.getRespuestas().get(2));

                    //Crea un nuevo hilo y se ejecutará pasadas 2000 milisegundos (2 segundos).
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            procesarResultado();
                        }
                    }, DELAY_SIGUIENTE_PREGUNTA);

                    preguntaRespondida = false;
                }
            }
        });

        ArrayList<Respuesta> respostes = new ArrayList<>();

        Respuesta respuesta1 = new Respuesta("resposta1", true);
        Respuesta respuesta2 = new Respuesta("resposta2", false);
        Respuesta respuesta3 = new Respuesta("resposta3", false);

        respostes.add(respuesta1);
        respostes.add(respuesta2);
        respostes.add(respuesta3);

        preguntas = new ArrayList<>();

        Pregunta pregunta1 = new Pregunta(1, "Pregunta1", "es", "agua", respostes);
        Pregunta pregunta2 = new Pregunta(2, "Pregunta2", "es", "agua", respostes);
        Pregunta pregunta3 = new Pregunta(3, "Pregunta3", "es", "agua", respostes);
        Pregunta pregunta4 = new Pregunta(4, "Pregunta4", "es", "agua", respostes);
        Pregunta pregunta5 = new Pregunta(5, "Pregunta5", "es", "agua", respostes);
        Pregunta pregunta6 = new Pregunta(6, "Pregunta6", "es", "agua", respostes);
        Pregunta pregunta7 = new Pregunta(7, "Pregunta7", "es", "agua", respostes);
        Pregunta pregunta8 = new Pregunta(8, "Pregunta8", "es", "agua", respostes);
        Pregunta pregunta9 = new Pregunta(9, "Pregunta9", "es", "agua", respostes);

        preguntas.add(pregunta1);
        preguntas.add(pregunta2);
        preguntas.add(pregunta3);
        preguntas.add(pregunta4);
        preguntas.add(pregunta5);
        preguntas.add(pregunta6);
        preguntas.add(pregunta7);
        preguntas.add(pregunta8);
        preguntas.add(pregunta9);

        cargarPregunta();
    }

    @Override
    public void onBackPressed()
    {
        //No hacer nada cuando el jugador hace click en el botón back.
    }

    private void cargarPregunta()
    {
        Paris.style(btnRespuesta1).apply(R.style.BotonesBordesRedondeadosRespuesta);
        Paris.style(btnRespuesta2).apply(R.style.BotonesBordesRedondeadosRespuesta);
        Paris.style(btnRespuesta3).apply(R.style.BotonesBordesRedondeadosRespuesta);

        if (preguntas.size() > 0)
        {
            do
            {
                pregunta = seleccionarPreguntaAleatoria();
            }
            while(pregunta.isMostrada()); //Escogemos una pregunta que no este mostrada.

            pregunta.setMostrada(true); //Ponemos la pregunta mostrada en true.

            textViewPregunta.setText(pregunta.getPregunta());
            btnRespuesta1.setText(pregunta.getRespuestas().get(0).getRespuesta());
            btnRespuesta2.setText(pregunta.getRespuestas().get(1).getRespuesta());
            btnRespuesta3.setText(pregunta.getRespuestas().get(2).getRespuesta());

            iniciarContador();
        }
    }

    /**
     * Comprobar si el usuario ha respondido correctamente.
     * Pasamos null si no ha respondido ningúna pregunta, en esta caso cuenta como que ha respondido mal.
     * Quita la vida del personaje o del enemigo y luego muestra la respuesta correcta y las incorrectas.
     * @param respuesta La respuesta que ha seleccionado el jugador.
     */
    private void procesarRespuesta(Respuesta respuesta)
    {
        if (respuesta != null)
        {
            if (respuesta.isCorrecta()) quitarVida(null, enemigo);
            else quitarVida(personaje, null);
        }
        else
        {
            quitarVida(personaje, null);
        }

        barra_progreso_animacion.cancel();

        mostrarCorrecta(pregunta);
    }

    /**
     * Seleccionamos una pregunta aleatoria.
     * @return La pregunta escogida por el random.
     */
    private Pregunta seleccionarPreguntaAleatoria()
    {
        return this.preguntas.get(new Random().nextInt(preguntas.size()));
    }

    /**
     * Iniciar la cuenta regresiva del progress bar.
     */
    private void iniciarContador()
    {
        barra_progreso_animacion = ObjectAnimator.ofInt(barra_progreso, "progress", 100, 0);
        barra_progreso_animacion.setInterpolator(new LinearInterpolator());
        barra_progreso_animacion.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {}

            @Override
            public void onAnimationEnd(Animator animation)
            {
                if (!preguntaRespondida)
                {
                    procesarRespuesta(null);

                    if (personaje.getVidas() != 0 && enemigo.getVidas() != 0)
                    {
                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                procesarResultado();
                            }
                        }, DELAY_SIGUIENTE_PREGUNTA);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {}

            @Override
            public void onAnimationRepeat(Animator animation)
            {}
        });

        barra_progreso_animacion.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                if (barra_progreso.getProgress() <= 30) barra_progreso.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bar_rojo));
                else barra_progreso.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bar_verde));
            }
        });

        barra_progreso_animacion.setDuration(TIEMPO_MAXIMO);
        barra_progreso_animacion.start();
    }

    /**
     * Cargar las vidas del personaje y del enemigo en el activity.
     * @param personaje El personaje que va a batallar.
     * @param enemigo El enemigo que va a batallar.
     */
    private void cargarVidas(@NotNull Personaje personaje, Enemigo enemigo)
    {
        vidasPersonaje = findViewById(R.id.vidasPersonaje);
        vidasEnemigo = findViewById(R.id.vidasEnemigo);

        //Cargar vidas del personaje.
        for (int i = 0; i < personaje.getVIDA_MAXIMA(); i++)
        {
            ImageView vida = new ImageView(this);
            vida.setImageResource(R.drawable.corazon);
            vidasPersonaje.addView(vida);
        }

        //Cargar vidas del enemigo.
        for (int i = 0; i < enemigo.getVIDA_MAXIMA(); i++)
        {
            ImageView vida = new ImageView(this);
            vida.setImageResource(R.drawable.corazon);
            vidasEnemigo.addView(vida);
        }
    }

    /**
     * Quitaremos una vida al personaje o al enemigo, y producirá un efecto de parpadeo a la imagen del personaje o enemigo.
     * @param personaje El personaje que vamos a quitar vida, si no queremos quitar vida al personaje, pasamos null.
     * @param enemigo El enemigo que vamos a quitar vida, si no queremos quitar vida al enemigo, pasamos null.
     */
    private void quitarVida(Personaje personaje, Enemigo enemigo)
    {
        if (personaje != null)
        {
            personaje.quitarVida(1);

            ImageView vida = (ImageView) vidasPersonaje.getChildAt(personaje.getVidas());
            vida.setImageResource(R.drawable.corazon_vacio);

            effectoBlink(batallaImagenPersonaje);
        }
        else if (enemigo != null)
        {
            enemigo.quitarVida(1);

            ImageView vida = (ImageView) vidasEnemigo.getChildAt(enemigo.getVidas());
            vida.setImageResource(R.drawable.corazon_vacio);

            effectoBlink(batallaImagenEnemigo);
        }
    }

    /**
     * Cambiar el color de los botones de respuestas para indicar cuál es la correcta (verde claro) y las incorrectas (rojo).
     * @param pregunta La pregunta actual.
     */
    @SuppressWarnings("deprecation") //Eliminar la advertencia de usar métodos en desuso.
    private void mostrarCorrecta(@NotNull Pregunta pregunta)
    {
        for (int i = 0; i < pregunta.getRespuestas().size(); i++)
        {
            if(pregunta.getRespuestas().get(i).isCorrecta())
            {
                int id = getResources().getIdentifier("btnRespuesta" + (i + 1), "id", getPackageName());
                Button btn = findViewById(id);
                Paris.style(btn).apply(R.style.BotonesBordesRedondeadosRespuestaCorrecta);
            }
            else
            {
                int id = getResources().getIdentifier("btnRespuesta" + (i + 1), "id", getPackageName());
                Button btn = findViewById(id);
                Paris.style(btn).apply(R.style.BotonesBordesRedondeadosRespuestaIncorrecta);
            }
        }
    }

    /**
     * Procesar el resultado de la partida. Si gana el jugador vuelve al mapa, si no gana vuelve al menú.
     * Si nadie ha perdido toda la vida, seguimos con las preguntas.
     */
    private void procesarResultado()
    {
        if (personaje.getVidas() != 0 && enemigo.getVidas() != 0)
        {
            cargarPregunta();
            btnRespuesta1.setEnabled(true);
            btnRespuesta2.setEnabled(true);
            btnRespuesta3.setEnabled(true);
        }
        else
        {
            if (personaje.getVidas() == 0)
            {
                final Dialog dialogDerrota = new MiDialogPersonalizado(BatallaActivity.this, R.layout.dialog_derrota);

                Button btnVolverMenu = dialogDerrota.findViewById(R.id.btnVolverMenu);

                btnVolverMenu.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        startActivity(new Intent(BatallaActivity.this, MenuActivity.class));
                        dialogDerrota.dismiss();
                        finish();
                    }
                });

                dialogDerrota.show();
            }
            else
            {
                final Dialog dialogVictoria = new MiDialogPersonalizado(BatallaActivity.this, R.layout.dialog_victoria);

                Button btnVolverMapa = dialogVictoria.findViewById(R.id.btnVolverMapa);

                btnVolverMapa.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(BatallaActivity.this, MapaActivity.class);
                        intent.putExtra(PERSONAJE, personaje);
                        setResult(RESULT_OK, intent);
                        dialogVictoria.dismiss();
                        finish();
                    }
                });

                dialogVictoria.show();
            }
        }
    }

    /**
     * Crear un efecto de parpadeo a una view.
     */
    private void effectoBlink(View view)
    {
        ObjectAnimator parpadeo = ObjectAnimator.ofInt(view, "visibility", View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.VISIBLE);
        parpadeo.setDuration(1500);
        parpadeo.setInterpolator(new LinearInterpolator());
        parpadeo.start();
    }
}
package com.example.edujoc_cepsoft;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
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
    public static final String PREGUNTAS = "preguntas";

    private ProgressBar barra_progreso;
    private CountDownTimer contador;

    private Button btnRespuesta1, btnRespuesta2, btnRespuesta3;

    private TextView textViewPregunta;

    private ArrayList<Pregunta> preguntas;
    private Pregunta pregunta;

    private LinearLayout vidasPersonaje, vidasEnemigo;

    private Enemigo enemigo;
    private Personaje personaje;

    private boolean preguntaRespondida = false;

    private String jugador;

    private int numBatalla;

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
        numBatalla = intent.getIntExtra(NUMERO_BATALLA, -1);
        preguntas           = (ArrayList<Pregunta>) intent.getExtras().getSerializable(PREGUNTAS);

        //region TESTS
        /*
        personaje = new Personaje(1, "TEST", "es", ".//personajes//img//trump_es_3.png", "asdasd");
        enemigo = new Enemigo(R.drawable.enemigo_agua, "Vaporeon");
        jugador = "TEST";
        numeroBatalla = 1;
        preguntas = new ArrayList<>();
        ArrayList<Respuesta> respuestas = new ArrayList<>();
        respuestas.add(new Respuesta("1", true));
        respuestas.add(new Respuesta("2", false));
        respuestas.add(new Respuesta("3", false));
        preguntas.add(new Pregunta(1, "PREGUNTA1", "es", "agua", respuestas));
        preguntas.add(new Pregunta(2, "PREGUNTA2", "es", "agua", respuestas));
        preguntas.add(new Pregunta(3, "PREGUNTA3", "es", "agua", respuestas));
        preguntas.add(new Pregunta(4, "PREGUNTA4", "es", "agua", respuestas));
        preguntas.add(new Pregunta(5, "PREGUNTA5", "es", "agua", respuestas));
        preguntas.add(new Pregunta(6, "PREGUNTA6", "es", "agua", respuestas));
        preguntas.add(new Pregunta(7, "PREGUNTA7", "es", "agua", respuestas));
        preguntas.add(new Pregunta(8, "PREGUNTA8", "es", "agua", respuestas));
        preguntas.add(new Pregunta(9, "PREGUNTA9", "es", "agua", respuestas));
         */
        //endregion

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

        batalla.append(" " + (numBatalla < 6 ? numBatalla : "final"));

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

        iniciarContador();
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

            contador.start();
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

        contador.cancel();

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
        contador = new CountDownTimer(TIEMPO_MAXIMO, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                barra_progreso.setProgress((int) (millisUntilFinished / 1000) + 1);

                if (barra_progreso.getProgress() <= 10) barra_progreso.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bar_rojo));
                else barra_progreso.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bar_verde));
            }

            @Override
            public void onFinish()
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
        };

        barra_progreso.setMax((int) (TIEMPO_MAXIMO / 1000));
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

        //Cargar vida actual del personaje.
        for (int i = 0; i < personaje.getVIDA_MAXIMA(); i++)
        {
            if (personaje.getVIDA_MAXIMA() > personaje.getVidas())
            {
                ImageView vida = new ImageView(this);

                if (i < personaje.getVidas()) vida.setImageResource(R.drawable.corazon);
                else vida.setImageResource(R.drawable.corazon_vacio);

                vidasPersonaje.addView(vida);
            }
            else
            {
                ImageView vida = new ImageView(this);
                vida.setImageResource(R.drawable.corazon);
                vidasPersonaje.addView(vida);
            }
        }

        //Cargar vida actual del enemigo.
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
            else if(personaje.getVidas() > 0 && numBatalla == 6)
            {
                final Dialog dialogVictoriaFinal = new MiDialogPersonalizado(BatallaActivity.this, R.layout.dialog_victoria_final);

                Button btnVolverMenu = dialogVictoriaFinal.findViewById(R.id.btnVolverMenuDos);

                btnVolverMenu.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        startActivity(new Intent(BatallaActivity.this, MenuActivity.class));
                        dialogVictoriaFinal.dismiss();
                        finish();
                    }
                });

                dialogVictoriaFinal.show();
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
                        intent.putExtra(PREGUNTAS, preguntas);
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
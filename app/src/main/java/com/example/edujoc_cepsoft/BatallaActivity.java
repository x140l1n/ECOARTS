package com.example.edujoc_cepsoft;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
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
    private final long TIEMPO_MAXIMO = 30000; //Máximo 30 segundos.
    private ProgressBar barra_progreso;
    private ObjectAnimator barra_progreso_animacion;

    private Button btnRespuesta1;
    private Button btnRespuesta2;
    private Button btnRespuesta3;

    private TextView textViewPregunta;

    private ArrayList<Pregunta> preguntas;
    private Pregunta pregunta;

    private Enemigo enemigo;
    private Personaje personaje;

    private boolean preguntaRespondida;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batalla);

        GifHelper.loadGif(this, R.drawable.fondo_principal_animado, (ImageView) findViewById(R.id.fondoGif));

        personaje           = new Personaje(1, "trump", "es", "test", "test");
        enemigo             = new Enemigo(123123, "Devorático");

        barra_progreso      = findViewById(R.id.progress_bar);
        textViewPregunta    = findViewById(R.id.textViewPregunta);
        btnRespuesta1       = findViewById(R.id.btnRespuesta1);
        btnRespuesta2       = findViewById(R.id.btnRespuesta2);
        btnRespuesta3       = findViewById(R.id.btnRespuesta3);

        btnRespuesta1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                barra_progreso_animacion.cancel();

                if (pregunta.getRespuestas().get(0).isCorrecta()) enemigo.quitarVida(1);
                else personaje.quitarVida(1);

                mostrarCorrecta(pregunta);

                preguntaRespondida = true;
            }
        });

        btnRespuesta2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                barra_progreso_animacion.cancel();

                if (pregunta.getRespuestas().get(0).isCorrecta()) enemigo.quitarVida(1);
                else personaje.quitarVida(1);

                mostrarCorrecta(pregunta);

                preguntaRespondida = true;
            }
        });

        btnRespuesta3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                barra_progreso_animacion.cancel();

                if (pregunta.getRespuestas().get(0).isCorrecta()) enemigo.quitarVida(1);
                else personaje.quitarVida(1);

                mostrarCorrecta(pregunta);

                preguntaRespondida = true;
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

        preguntas.add(pregunta1);
        preguntas.add(pregunta2);
        preguntas.add(pregunta3);

        cargarPregunta();
    }

    private void cargarPregunta()
    {
        /*
        1.- random pregunta
        2.- Carregar textView i Buton respostes
        3.- iniciarCronometro()
        4.- DELAY onPAUSE???
        click a boto -> carregarPregunta()
        */
        do
        {
            pregunta = seleccionarPreguntaAleatoria(preguntas);
        }
        while(pregunta.isMostrada()); //Escogemos una pregunta que no este mostrada.

        pregunta.setMostrada(true); //Ponemos la pregunta mostrada en true.

        textViewPregunta.setText(pregunta.getPregunta());
        btnRespuesta1.setText(pregunta.getRespuestas().get(0).getRespuesta());
        btnRespuesta2.setText(pregunta.getRespuestas().get(1).getRespuesta());
        btnRespuesta3.setText(pregunta.getRespuestas().get(2).getRespuesta());

        iniciarContador();
    }

    /**
     * Seleccionamos una pregunta aleatoria.
     * @param preguntas La lista de preguntas donde vamos a escoger una pregunta.
     * @return La pregunta escogida por el random.
     */
    private Pregunta seleccionarPreguntaAleatoria(@NotNull ArrayList<Pregunta> preguntas)
    {
        return preguntas.get(new Random().nextInt(preguntas.size()));
    }

    private void iniciarContador()
    {
        barra_progreso_animacion = ObjectAnimator.ofInt(barra_progreso, "progress", 100, 0);
        barra_progreso_animacion.setInterpolator(new LinearInterpolator());
        barra_progreso_animacion.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                mostrarCorrecta(pregunta);
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
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
}
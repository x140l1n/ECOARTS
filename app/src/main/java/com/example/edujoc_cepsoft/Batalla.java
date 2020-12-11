package com.example.edujoc_cepsoft;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.edujoc_cepsoft.Data.Enemigo;
import com.example.edujoc_cepsoft.Data.Personaje;
import com.example.edujoc_cepsoft.Data.Pregunta;
import com.example.edujoc_cepsoft.Data.Respuesta;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Batalla extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batalla);

        Personaje personaje = new Personaje(1, "Trump", "es", "hhh", "jhjh", 5);
        Enemigo enemigo = new Enemigo(R.drawable.trump_es);
        ArrayList<Respuesta> respostes = new ArrayList<Respuesta>();
        Respuesta respuesta1 = new Respuesta("resposta1", true);
        Respuesta respuesta2 = new Respuesta("resposta2", false);
        Respuesta respuesta3 = new Respuesta("resposta3", false);
        respostes.add(respuesta1);
        respostes.add(respuesta2);
        respostes.add(respuesta3);

        ArrayList<Pregunta>preguntas = new ArrayList<Pregunta>();
        Pregunta pregunta1 = new Pregunta(1, "Pregunta1", "es", "agua", respostes);
        Pregunta pregunta2 = new Pregunta(2, "Pregunta2", "es", "agua", respostes);
        Pregunta pregunta3 = new Pregunta(3, "Pregunta3", "es", "agua", respostes);
        preguntas.add(pregunta1);
        preguntas.add(pregunta2);
        preguntas.add(pregunta3);

        do{
            carregarPregunta(preguntas, personaje, enemigo);
        }while(personaje.getVidas()>0&&enemigo.getVidas()>0);



    }
    private void carregarPregunta(ArrayList preguntas, final Personaje personaje, final Enemigo enemigo){
        /*
        1.- random pregunta
        2.- Carregar textView i Buton respostes
        3.- iniciarCronometro()
        4.- DELAY oPAUSE???
        click a boto -> carregarPregunta()
        * */

        Pregunta pregunta;
        do{
            pregunta = seleccionarPreguntaAleatoria(preguntas);
        }while(pregunta.isMostrada());
        pregunta.setMostrada(true);
        TextView tvPregunta = findViewById(R.id.tvPregunta);
        Button btnRespuesta1 = findViewById(R.id.respuesta1);
        Button btnRespuesta2 = findViewById(R.id.respuesta2);
        Button btnRespuesta3 = findViewById(R.id.respuesta3);

        tvPregunta.setText(pregunta.getPregunta());
        btnRespuesta1.setText(pregunta.getRespuestas().get(0).getRespuesta());
        btnRespuesta2.setText(pregunta.getRespuestas().get(1).getRespuesta());
        btnRespuesta3.setText(pregunta.getRespuestas().get(2).getRespuesta());

        final ObjectAnimator animation = iniciarCronometro();
        final Pregunta finalPregunta = pregunta;
        btnRespuesta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.cancel();
                if(!comprobarRespuesta(finalPregunta.getRespuestas().get(0))){
                    int vidas = personaje.getVidas();
                    vidas--;
                    personaje.setVidas(vidas);
                }else{
                    int vidasEnemigo = enemigo.getVidas();
                    vidasEnemigo--;
                    enemigo.setVidas(vidasEnemigo);
                }

            }
        });
        btnRespuesta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.cancel();
                if(!comprobarRespuesta(finalPregunta.getRespuestas().get(1))){
                    int vidas = personaje.getVidas();
                    vidas--;
                    personaje.setVidas(vidas);
                }else{
                    int vidasEnemigo = enemigo.getVidas();
                    vidasEnemigo--;
                    enemigo.setVidas(vidasEnemigo);
                }

            }
        });
        btnRespuesta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.cancel();
                if(!comprobarRespuesta(finalPregunta.getRespuestas().get(2))){
                    int vidas = personaje.getVidas();
                    vidas--;
                    personaje.setVidas(vidas);
                }else{
                    int vidasEnemigo = enemigo.getVidas();
                    vidasEnemigo--;
                    enemigo.setVidas(vidasEnemigo);
                }

            }
        });
    }
    private Pregunta seleccionarPreguntaAleatoria(ArrayList preguntas){
        int totalElements = preguntas.size();
        int element = new Random().nextInt(totalElements);
        Pregunta pregunta = (Pregunta)preguntas.get(element);
        return pregunta;
    }
    private ObjectAnimator iniciarCronometro(){
        ProgressBar pb = findViewById(R.id.progressBar);
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", 30000, 0);
        animation.setDuration(30000);

        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //do something when the countdown is complete
            }

            @Override
            public void onAnimationCancel(Animator animator) { }

            @Override
            public void onAnimationRepeat(Animator animator) { }
        });
        animation.start();

        return animation;

    }
    private boolean comprobarRespuesta(Respuesta respuesta){
        boolean acierto = false;
        if(respuesta.isCorrecte()){
            acierto = true;
        }
        return acierto;
    }
}
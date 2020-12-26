package com.example.edujoc_cepsoft;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import com.example.edujoc_cepsoft.Data.Enemigo;
import com.example.edujoc_cepsoft.Data.Personaje;

import org.jetbrains.annotations.NotNull;

import com.example.edujoc_cepsoft.Data.Pregunta;
import com.example.edujoc_cepsoft.Helpers.EffectSoundHelper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MapaActivity extends MiActivityPersonalizado
{
    public static final String PERSONAJE = "personaje";
    public static final String NOMBRE_JUGADOR = "nombre_jugador";
    public static final String NIVEL = "nivel";

    private Button btnJugar;
    private ImageButton btnAjustes;
    private View viewPersonaje;
    private String nivel;

    private int numBatalla = 1;

    private ArrayList<Enemigo> enemigos;
    private ArrayList<Pregunta> preguntas;

    private Personaje personaje;

    private LinearLayout vidasPersonaje;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        id_musica = R.raw.mapa;
        musicaFondo = null;

        Intent intent = getIntent();

        personaje = (Personaje)intent.getSerializableExtra(PERSONAJE);
        String nombre = intent.getStringExtra(NOMBRE_JUGADOR);

        nivel               = intent.getStringExtra(NIVEL);
        viewPersonaje       = findViewById(R.id.personaje);
        btnAjustes          = findViewById(R.id.btnAjustes);
        vidasPersonaje      = findViewById(R.id.vidasPersonaje);
        btnJugar            = viewPersonaje.findViewById(R.id.btnJugar);
        TextView nivelMapa  = findViewById(R.id.nivelMapa);

        if (nivel.equals("facil")) nivelMapa.append(" " + getString(R.string.facil));
        else nivelMapa.append(" " + getString(R.string.dificil));

        final TextView nombreJugador = viewPersonaje.findViewById(R.id.nombreJugador);
        ImageView personajeMapa = viewPersonaje.findViewById(R.id.personajeMapa);

        nombreJugador.setText(nombre);
        personajeMapa.setImageBitmap(personaje.obtenerImagen(this));

        enemigos = cargarEnemigos();
        preguntas = cargarPreguntas();

        btnJugar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EffectSoundHelper.reproducirEfecto(MapaActivity.this, R.raw.boton_click);

                final Dialog dialogInfoEnemigo = new MiDialogPersonalizado(MapaActivity.this, R.layout.dialog_info_enemigo);
                Button btnComenzarBatalla = dialogInfoEnemigo.findViewById(R.id.btnComenzarBatalla);
                ImageButton btnCancelar = dialogInfoEnemigo.findViewById(R.id.btnCancelar);

                final Enemigo enemigo = enemigos.get(numBatalla - 1);

                LinearLayout fondoDialog = dialogInfoEnemigo.findViewById(R.id.fondoDialog);
                ImageView imagenEnemigo = dialogInfoEnemigo.findViewById(R.id.imagenEnemigo);
                TextView textViewNombreEnemigo = dialogInfoEnemigo.findViewById(R.id.textViewNombreEnemigo);
                TextView textViewNumBatalla = dialogInfoEnemigo.findViewById(R.id.textViewNumBatalla);

                Drawable background = fondoDialog.getBackground();

                GradientDrawable shapeDrawable = (GradientDrawable) background;
                shapeDrawable.setColor(ContextCompat.getColor(dialogInfoEnemigo.getContext(), enemigo.getColorFondo()));

                imagenEnemigo.setImageResource(enemigo.getImagen());
                textViewNombreEnemigo.setText(enemigo.getNombre());
                textViewNumBatalla.append(" " + (numBatalla < 6 ? numBatalla : "final"));

                btnComenzarBatalla.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        EffectSoundHelper.reproducirEfecto(MapaActivity.this, R.raw.boton_click);

                        Intent intent = new Intent(MapaActivity.this, BatallaActivity.class);

                        Bundle b = new Bundle();
                        b.putSerializable(BatallaActivity.PREGUNTAS, preguntas);

                        intent.putExtras(b);
                        intent.putExtra(BatallaActivity.ENEMIGO, enemigo);
                        intent.putExtra(BatallaActivity.PERSONAJE, personaje);
                        intent.putExtra(BatallaActivity.NUMERO_BATALLA, numBatalla);
                        intent.putExtra(BatallaActivity.JUGADOR, nombreJugador.getText());

                        dialogInfoEnemigo.dismiss();

                        if(numBatalla < 6) startActivityForResult(intent, BatallaActivity.BATALLA_ACTIVITY);
                        else startActivity(intent);
                    }
                });

                btnCancelar.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        EffectSoundHelper.reproducirEfecto(MapaActivity.this, R.raw.boton_click);

                        dialogInfoEnemigo.dismiss();
                    }
                });

                dialogInfoEnemigo.show();
            }
        });

        btnAjustes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EffectSoundHelper.reproducirEfecto(MapaActivity.this, R.raw.boton_click);

                final Dialog dialogAjuste = new MiDialogPersonalizado(MapaActivity.this, R.layout.dialog_ajuste_partida);

                Button btnAbandonarPartida = dialogAjuste.findViewById(R.id.btnAbandonarPartida);
                ImageButton btnCancelar = dialogAjuste.findViewById(R.id.btnCancelar);
                SwitchCompat switchEfecto = dialogAjuste.findViewById(R.id.switchEfecto);
                SwitchCompat switchMusica = dialogAjuste.findViewById(R.id.switchMusica);

                switchEfecto.setChecked(EffectSoundHelper.reproducirEfecto);

                switchEfecto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        EffectSoundHelper.reproducirEfecto = isChecked;
                    }
                });

                switchMusica.setChecked(reproducirMusica);

                switchMusica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        reproducirMusica = isChecked;

                        if (!reproducirMusica) musicaFondo.setVolume(0f, 0f);
                        else
                        {
                            if (musicaFondo != null) musicaFondo.setVolume(0.5f, 0.5f);
                            else
                            {
                                musicaFondo = MediaPlayer.create(MapaActivity.this, id_musica);
                                musicaFondo.setVolume(0.5f, 0.5f);
                                musicaFondo.setLooping(true);
                                musicaFondo.start();
                            }
                        }
                    }
                });

                btnAbandonarPartida.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        EffectSoundHelper.reproducirEfecto(MapaActivity.this, R.raw.boton_click);

                        final Dialog dialogAbandonar = new MiDialogPersonalizado(MapaActivity.this, R.layout.dialog_abandonar_partida);

                        ImageButton btnAbandonar = dialogAbandonar.findViewById(R.id.btnAbandonar);
                        ImageButton btnCancelar = dialogAbandonar.findViewById(R.id.btnCancelar);

                        btnAbandonar.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                EffectSoundHelper.reproducirEfecto(MapaActivity.this, R.raw.boton_click);

                                startActivity(new Intent(MapaActivity.this, MenuActivity.class));

                                dialogAjuste.dismiss();
                                dialogAbandonar.dismiss();

                                finish();
                            }
                        });

                        btnCancelar.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                EffectSoundHelper.reproducirEfecto(MapaActivity.this, R.raw.boton_click);

                                dialogAbandonar.dismiss();
                            }
                        });

                        dialogAbandonar.show();
                    }
                });

                btnCancelar.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        EffectSoundHelper.reproducirEfecto(MapaActivity.this, R.raw.boton_click);

                        dialogAjuste.dismiss();
                    }
                });

                dialogAjuste.show();
            }
        });

        cargarVidas(personaje);
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
                    Personaje personaje = (Personaje) data.getSerializableExtra(BatallaActivity.PERSONAJE);

                    preguntas = (ArrayList) data.getSerializableExtra(BatallaActivity.PREGUNTAS);

                    if (nivel.equals("facil")) personaje.setVida(personaje.getVIDA_MAXIMA()); //Restablecer las vidas.

                    this.personaje = personaje;

                    if (this.personaje.getVIDA_MAXIMA() != this.personaje.getVida()) quitarVida(this.personaje);

                    numBatalla++;

                    btnJugar.setEnabled(false);

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
            case 3:
                ObjectAnimator oa4 = ObjectAnimator.ofFloat(viewPersonaje, View.TRANSLATION_Y, -300f);
                ObjectAnimator oa5 = ObjectAnimator.ofFloat(viewPersonaje, View.TRANSLATION_X, 100f);
                ObjectAnimator oa6 = ObjectAnimator.ofFloat(viewPersonaje, View.TRANSLATION_Y, -450f);

                as.playSequentially(oa4, oa5, oa6);
                break;
            case 4:
                ObjectAnimator oa7 = ObjectAnimator.ofFloat(viewPersonaje, View.TRANSLATION_X, 530f);
                ObjectAnimator oa8 = ObjectAnimator.ofFloat(viewPersonaje, View.TRANSLATION_Y, -850f);

                as.playSequentially(oa7, oa8);
                break;

            case 5:
                ObjectAnimator oa9 = ObjectAnimator.ofFloat(viewPersonaje, View.TRANSLATION_X, 270f);
                ObjectAnimator oa10 = ObjectAnimator.ofFloat(viewPersonaje, View.TRANSLATION_Y, -900f);
                ObjectAnimator oa11 = ObjectAnimator.ofFloat(viewPersonaje, View.TRANSLATION_X, 0f);

                as.playSequentially(oa9, oa10, oa11);
                break;
            case 6:
                ObjectAnimator oa12 = ObjectAnimator.ofFloat(viewPersonaje, View.TRANSLATION_Y, -1100f);
                ObjectAnimator oa13 = ObjectAnimator.ofFloat(viewPersonaje, View.TRANSLATION_X, 270f);

                as.playSequentially(oa12, oa13);
                break;
        }

        as.setDuration(1200);
        as.start();
    }

    @NotNull
    private ArrayList<Enemigo> cargarEnemigos()
    {
        ArrayList<Enemigo> enemigos = new ArrayList<Enemigo>();

        enemigos.add(new Enemigo(R.drawable.enemigo_agua, "Gyarados", R.color.colorAzul, 3));
        enemigos.add(new Enemigo(R.drawable.enemigo_bosque, "Shiftry", R.color.colorVerdeOscuro, 3));
        enemigos.add(new Enemigo(R.drawable.enemigo_energia, "Zapdos", R.color.colorAmarillo, 3));
        enemigos.add(new Enemigo(R.drawable.enemigo_gas, "Weezing", R.color.colorMorado,3));
        enemigos.add(new Enemigo(R.drawable.enemigo_plastico, "Unown", R.color.colorGrisOscuro,3));
        enemigos.add(new Enemigo(R.drawable.enemigo_residuo, "Garbodor", R.color.colorPistacho,5));

        return enemigos;
    }

    private ArrayList<Pregunta> cargarPreguntas()
    {
        ArrayList<Pregunta> preguntas = null;

        Locale lang = this.getResources().getConfiguration().locale;

        String rutaFicheroJson = this.getFilesDir() + File.separator + "preguntas" + File.separator;

        switch (lang.getLanguage())
        {
            case "es": rutaFicheroJson += "preguntas_es.json"; break;
            case "ca": rutaFicheroJson += "preguntas_ca.json"; break;
            case "en": rutaFicheroJson += "preguntas_en.json"; break;
            default:   rutaFicheroJson  = null;                break;
        }

        if (rutaFicheroJson != null)
        {
            BufferedReader br = null;

            try
            {
                br = new BufferedReader(new FileReader(rutaFicheroJson));

                preguntas = new ArrayList<>(Arrays.asList(new Gson().fromJson(br, Pregunta[].class)));
            }
            catch (FileNotFoundException ex)
            {
                System.err.println("Fichero json no encontrado:\n" + ex.getMessage());
            }
            finally //Si ha saltado un error o no, cerraremos igualmente el BufferedReader.
            {
                try
                {
                    if (br != null) br.close();
                }
                catch (IOException ex)
                {
                    System.err.println("Error al cerrar el Buffered Reader:\n" + ex.getMessage());
                }
            }
        }
        else
        {
            System.err.println("No se ha podiddo obtener la ruta del fichero json. Idioma detectado: " + lang.getLanguage());
        }

        return preguntas;
    }

    /**
     * Cargar las vidas del personaje en el activity.
     * @param personaje El personaje que va a batallar.
     */
    private void cargarVidas(@NotNull Personaje personaje)
    {
        vidasPersonaje = findViewById(R.id.vidasPersonaje);

        //Cargar vida actual del personaje.
        for (int i = 0; i < personaje.getVIDA_MAXIMA(); i++)
        {
            ImageView vida = new ImageView(this);
            vida.setImageResource(R.drawable.corazon);
            vidasPersonaje.addView(vida);
        }
    }

    /**
     * Ponemos las vidas que le quedan al personaje.
     * @param personaje El personaje que vamos a quitar vida, si no queremos quitar vida al personaje, pasamos null.
     */
    private void quitarVida(Personaje personaje)
    {
        if (personaje != null)
        {
            for (int i = personaje.getVIDA_MAXIMA() - 1; i > personaje.getVida() - 1; i--)
            {
                ImageView vida = (ImageView) vidasPersonaje.getChildAt(i);
                vida.setImageResource(R.drawable.corazon_vacio);
            }
        }
    }
}
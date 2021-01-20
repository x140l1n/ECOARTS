package com.example.edujoc_cepsoft.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.edujoc_cepsoft.Data.Personaje;
import com.example.edujoc_cepsoft.Helpers.EffectSoundHelper;
import com.example.edujoc_cepsoft.MiDialogPersonalizado;
import com.example.edujoc_cepsoft.R;

import java.util.List;

public class SelectPersonajeAdapter extends ArrayAdapter<Personaje>
{
    private final Context context;

    public SelectPersonajeAdapter(@NonNull Context context, List<Personaje> personajes)
    {
        super(context, 0, personajes);

        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent)
    {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.plantilla_grid_personajes, parent, false);

        ImageView imagenPersonaje = view.findViewById(R.id.imagenPersonaje);
        TextView txtViewNombrePersonaje = view.findViewById(R.id.txtViewNombrePersonaje);

        ImageButton btnConfirmar = view.findViewById(R.id.btnConfirmar);
        ImageButton btnInfo = view.findViewById(R.id.btnInformacion);

        btnConfirmar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EffectSoundHelper.reproducirEfecto(getContext(), R.raw.boton_click);

                deseleccionarTodo();

                view.setBackgroundResource(R.drawable.item_background_grid_personajes_seleccionado);

                getItem(position).setSeleccionado(true);

                //Actualizar la grid view.
                ((GridView) parent).invalidateViews();
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                EffectSoundHelper.reproducirEfecto(getContext(), R.raw.boton_click);

                final Dialog dialogInfo = new MiDialogPersonalizado(context, R.layout.dialog_info_personaje);

                ImageButton btnCancelar = dialogInfo.findViewById(R.id.btnCancelar);
                TextView txtViewNombrePersonaje = dialogInfo.findViewById(R.id.txtViewNombrePersonaje);
                TextView txtViewDescPersonaje = dialogInfo.findViewById(R.id.txtViewDescPersonaje);

                txtViewNombrePersonaje.setText(getItem(position).getNombre());
                txtViewDescPersonaje.setText(getItem(position).getDescripcion());

                //Esto es para poner el scroll bar.
                txtViewDescPersonaje.setMovementMethod(new ScrollingMovementMethod());

                btnCancelar.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        EffectSoundHelper.reproducirEfecto(getContext(), R.raw.boton_click);

                        dialogInfo.dismiss();
                    }
                });

                dialogInfo.show();
            }
        });

        if (getItem(position).isSeleccionado()) view.setBackgroundResource(R.drawable.item_background_grid_personajes_seleccionado);
        else view.setBackgroundResource(R.drawable.item_background_grid_personajes);

        imagenPersonaje.setImageBitmap(getItem(position).obtenerImagen(getContext()));

        txtViewNombrePersonaje.setText(getItem(position).getNombre());

        return view;
    }

    private void deseleccionarTodo()
    {
        for(int i = 0; i < getCount(); i++)
        {
            getItem(i).setSeleccionado(false);
        }
    }
}

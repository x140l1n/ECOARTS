package com.example.edujoc_cepsoft.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.edujoc_cepsoft.MiDialogPersonalizado;
import com.example.edujoc_cepsoft.R;

import java.io.File;
import java.util.List;

public class SelectPersonajeAdapter extends ArrayAdapter<Personaje>
{
    private Context context;

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
                deseleccionarTodo();

                view.setBackgroundResource(R.drawable.item_background_grid_personajes_seleccionado);

                getItem(position).setSeleccionado(true);

                ((GridView) parent).invalidateViews();
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final Dialog dialogInfo = new MiDialogPersonalizado(context, R.layout.dialog_info_personaje);

                ImageButton btnCancelar = dialogInfo.findViewById(R.id.btnCancelar);
                TextView txtViewNombrePersonaje = dialogInfo.findViewById(R.id.txtViewNombrePersonaje);
                TextView txtViewDescPersonaje = dialogInfo.findViewById(R.id.txtViewDescPersonaje);

                txtViewNombrePersonaje.setText(getItem(position).getNombre());
                txtViewDescPersonaje.setText(getItem(position).getDescripcion());

                txtViewDescPersonaje.setMovementMethod(new ScrollingMovementMethod());
                btnCancelar.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialogInfo.dismiss();
                    }
                });

                dialogInfo.show();
            }
        });

        if (getItem(position).isSeleccionado()) view.setBackgroundResource(R.drawable.item_background_grid_personajes_seleccionado);
        else view.setBackgroundResource(R.drawable.item_background_grid_personajes);

        //Obtener la ruta de la imagen del objeto personaje, replazamos el ./ por la ruta files de la app y convertimos todo en minúsculas.
        String rutaAbsolutaPersonaje = getItem(position).getRutaImagen().replace("./", getContext().getFilesDir() + File.separator).toLowerCase();

        //Obtener la imagen de la ruta y crear una imagen Bitmap.
        Bitmap bitmapPersonaje = BitmapFactory.decodeFile(rutaAbsolutaPersonaje);

        //Insertar la imagen.
        imagenPersonaje.setImageBitmap(bitmapPersonaje);

        txtViewNombrePersonaje.setText(getItem(position).getNombre());

        return view;
    }

    private void deseleccionarTodo()
    {
        for(int i = 0; i < super.getCount(); i++)
        {
            getItem(i).setSeleccionado(false);
        }
    }
}

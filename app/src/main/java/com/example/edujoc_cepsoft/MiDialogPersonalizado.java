package com.example.edujoc_cepsoft;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.example.edujoc_cepsoft.Helpers.SystemUIHelper;

/**
 * Clase Dialog personalizada. Creamos la clase para que en todas los Dialogs que se extienda de esta clase se oculte la barra de navegación y tenga un diseño predefinido.
 */
public class MiDialogPersonalizado extends Dialog {
    public MiDialogPersonalizado(@NonNull Context context, int id_layout) {
        super(context);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCancelable(false);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.setContentView(id_layout);
    }

    /**
     * Cuando el diálogo se muestra.
     */
    @Override
    public void show() {
        //Ocultar la barra de navegación.
        SystemUIHelper.ocultarBarraNavegacion(this.getWindow());

        //Hacer que el diálogo no tenga el foco.
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        //Una vez que el diálogo no tiene el foco mostramos sin la barra de navegación.
        super.show();

        //Hacer que el diálogo vuelva a tener el foco.
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        //Hacer que el dialogo tenga la máxima anchura posible.
        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
        windowParams.copyFrom(this.getWindow().getAttributes()); //Obtenemos los atributos actuales de la ventana de diálogo y copiamos al nuevo LayaoutParams.
        windowParams.width = (int) (this.getContext().getResources().getDisplayMetrics().widthPixels * 0.90); //El 90% de la anchura del activity.
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        this.getWindow().setAttributes(windowParams);
    }
}

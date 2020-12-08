package com.example.edujoc_cepsoft;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edujoc_cepsoft.Helpers.GifHelper;
import com.example.edujoc_cepsoft.Helpers.MediaPlayerHelper;

public class MenuActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        GifHelper.loadGif(this, R.drawable.background_animation, (ImageView) findViewById(R.id.fondoGif));

        final Button btnJugar = findViewById(R.id.btnJugar);
        final Button btnAjustes = findViewById(R.id.btnAjustes);
        final Button btnSalir = findViewById(R.id.btnSalir);

        btnJugar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MenuActivity.this, SelectPersonajeActivity.class));
            }
        });

        btnAjustes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MenuActivity.this, AjustesActivity.class));
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Dialog dialogSalir = new Dialog(MenuActivity.this);
                dialogSalir.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogSalir.setCancelable(false);
                dialogSalir.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
                dialogSalir.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogSalir.setContentView(R.layout.plantilla_dialog);

                dialogSalir.show();
            }
        });
    }


    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }
}
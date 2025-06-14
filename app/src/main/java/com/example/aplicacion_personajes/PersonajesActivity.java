package com.example.aplicacion_personajes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PersonajesActivity extends AppCompatActivity {
    private ImageView imagen;
    private TextView txtnombre, txtdescripcion;
    private Button btnanterior, btnsiguiente;

    private String[] nombres;
    private String[] descripciones;
    private String[] imagenesVertical;
    private String[] imagenesHorizontal;

    private int posicion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personajes);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Actualizacion con el maneo del estado
        if (savedInstanceState != null) {
            posicion = savedInstanceState.getInt("posicion", 0);
        }



        inicializarVistas();
        cargarDatos();
        botones();
        mostrarPersonaje();
    }

    private void inicializarVistas() {
        imagen = findViewById(R.id.imagen);
        txtnombre = findViewById(R.id.txtnombre);
        txtdescripcion = findViewById(R.id.txtdescripcion);
        btnanterior = findViewById(R.id.btnanterior);
        btnsiguiente = findViewById(R.id.btnsiguiente);
    }

    private void cargarDatos() {
        nombres = getResources().getStringArray(R.array.personajes_nombre);
        descripciones = getResources().getStringArray(R.array.personajes_descripcion);
        imagenesVertical = getResources().getStringArray(R.array.personajes_imagen_vertical);
        imagenesHorizontal = getResources().getStringArray(R.array.personajes_imagen_horizontal);

    }

    private void botones() {
        btnanterior.setOnClickListener(v -> {
            if (posicion > 0) {
                posicion--;
                mostrarPersonaje();
            }
        });

        btnsiguiente.setOnClickListener(v -> {
            if (posicion < nombres.length - 1) {
                posicion++;
                mostrarPersonaje();
            }
        });
    }

    private void mostrarPersonaje() {
        txtnombre.setText(nombres[posicion]);
        txtdescripcion.setText(descripciones[posicion]);

        int orientation = getResources().getConfiguration().orientation;
        String nombreImagen;

        //utilizo dos imagenes para cada personaje, una para horizontal y la otra para vertical
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if (imagenesHorizontal != null && imagenesHorizontal.length > posicion) {
                nombreImagen = imagenesHorizontal[posicion];
            } else {
                nombreImagen = imagenesVertical[posicion];
            }
        } else {
            nombreImagen = imagenesVertical[posicion];
        }

        //se busca el nombre de la imagen en la carpeta drawer, ya habia definido en unos string-array con los nombre de las imagenes segun la orientacion de la pantalla
        int resourceId = getResources().getIdentifier(nombreImagen, "drawable", getPackageName());
        if (resourceId != 0) {
            imagen.setImageResource(resourceId);
        }

        btnanterior.setEnabled(posicion > 0);
        btnsiguiente.setEnabled(posicion < nombres.length - 1);
    }

    //Actualizacion con el maneo del estado
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("posicion", posicion);
    }


}
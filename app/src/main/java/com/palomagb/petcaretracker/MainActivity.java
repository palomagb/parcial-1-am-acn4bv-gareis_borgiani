package com.palomagb.petcaretracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import android.content.SharedPreferences;
import android.text.InputType;

public class MainActivity extends AppCompatActivity {

    // componentes del XML
    private TextView tvHistorial, tvNombreMascota, tvDisplayEdad, tvDisplayPeso;
    private Button btnComida, btnAgua, btnPaseo, btnJuego, btnHigiene, btnMedicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // IDs del XML
        tvHistorial = findViewById(R.id.tv_historial);

        btnComida = findViewById(R.id.btn_comida);
        btnAgua = findViewById(R.id.btn_agua);
        btnPaseo = findViewById(R.id.btn_paseo);
        btnJuego = findViewById(R.id.btn_juego);
        btnHigiene = findViewById(R.id.btn_higiene);
        btnMedicacion = findViewById(R.id.btn_medicacion);

        tvNombreMascota = findViewById(R.id.tv_nombre_mascota);
        tvDisplayEdad = findViewById(R.id.tv_display_edad);
        tvDisplayPeso = findViewById(R.id.tv_display_peso);

        findViewById(R.id.fab_edit_profile).setOnClickListener(v -> mostrarDialogoEdicion());

        configurarBotones();
        cargarDatos();
    }

    private void configurarBotones() {
        btnComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarActividad("Ración de alimento servida.");
            }
        });

        btnAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarActividad("Cambio de agua fresca.");
            }
        });

        btnPaseo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarActividad("Paseo realizado.");
            }
        });

        btnJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarActividad("Sesión de juego completada.");
            }
        });

        btnHigiene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarActividad("Higiene/Baño realizado.");
            }
        });

        btnMedicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarActividad("Medicación administrada.");
            }
        });
    }

    // actualizar el historial
    private void registrarActividad(String actividad) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("America/Argentina/Cordoba"));
        String horaActual = sdf.format(new Date());
        String nuevoRegistro = "• " + horaActual + " - " + actividad;

        String historialActual = tvHistorial.getText().toString();

        // verifica si el historial esta vacío
        if (historialActual.equals(getString(R.string.msg_empty_history))) {
            tvHistorial.setText(nuevoRegistro); //vacio
        } else {
            tvHistorial.setText(nuevoRegistro + "\n" + historialActual); //con datos previos
        }
        guardarDatos();
    }

    //persistencia de datos
    private void guardarDatos() {
        SharedPreferences preferences = getSharedPreferences("PetCarePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("historial", tvHistorial.getText().toString());
        editor.putString("edad", tvDisplayEdad.getText().toString());
        editor.putString("peso", tvDisplayPeso.getText().toString());

        editor.apply();
    }

    // carga los datos al abrir app
    private void cargarDatos() {
        SharedPreferences preferences = getSharedPreferences("PetCarePrefs", MODE_PRIVATE);

        tvNombreMascota.setText("Polar");

        tvHistorial.setText(preferences.getString("historial", getString(R.string.msg_empty_history)));

        tvDisplayEdad.setText(preferences.getString("edad", "Edad: 4 años"));
        tvDisplayPeso.setText(preferences.getString("peso", "Peso: 6.5 kg"));
    }

    //edit
    private void mostrarDialogoEdicion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Actualizar datos de Polar");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        final EditText inputEdad = new EditText(this);
        inputEdad.setHint("Ej: 4");
        String edadActual = tvDisplayEdad.getText().toString().replaceAll("[^0-9]", "");
        inputEdad.setText(edadActual);
        layout.addView(inputEdad);

        final EditText inputPeso = new EditText(this);
        inputPeso.setHint("Ej: 6.5");
        inputPeso.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        String pesoActual = tvDisplayPeso.getText().toString().replace("Peso: ", "").replace(" kg", "");
        inputPeso.setText(pesoActual);
        layout.addView(inputPeso);

        builder.setView(layout);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nuevaEdad = inputEdad.getText().toString();
            String nuevoPeso = inputPeso.getText().toString();

            if (!nuevaEdad.isEmpty()) tvDisplayEdad.setText("Edad: " + nuevaEdad + " años");
            if (!nuevoPeso.isEmpty()) tvDisplayPeso.setText("Peso: " + nuevoPeso + " kg");

            guardarDatos();
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

}
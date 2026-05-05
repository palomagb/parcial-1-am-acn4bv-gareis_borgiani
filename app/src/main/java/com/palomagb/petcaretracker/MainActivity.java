package com.palomagb.petcaretracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    // componentes del XML
    private TextView tvHistorial;
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

        configurarBotones();
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
    }
}
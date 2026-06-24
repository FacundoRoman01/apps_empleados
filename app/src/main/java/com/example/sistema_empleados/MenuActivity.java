package com.example.sistema_empleados;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private Button btnIrAgregar;
    private Button btnIrLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        btnIrAgregar = findViewById(R.id.btnIrAgregar);
        btnIrLista = findViewById(R.id.btnIrLista);

        // asgregar Empleado
        btnIrAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this, AgregarEmpleadoActivity.class);
                startActivity(intent);
            }
        });

        // parA ver la lista
        btnIrLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuActivity.this, "Próximamente: Pantalla Lista", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package com.example.sistema_empleados;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AgregarEmpleadoActivity extends AppCompatActivity {


    private EditText etNombre, etPuesto, etSalario;
    private Button btnGuardar;


    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_empleado);

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        // Vincular XML con Java
        etNombre = findViewById(R.id.etNombre);
        etPuesto = findViewById(R.id.etPuesto);
        etSalario = findViewById(R.id.etSalario);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
            }
        });
    }

    private void guardarDatos() {

        String nombre = etNombre.getText().toString().trim();
        String puesto = etPuesto.getText().toString().trim();
        String salario = etSalario.getText().toString().trim();


        if (nombre.isEmpty()) {
            etNombre.setError("El nombre es obligatorio");
            return;
        }
        if (puesto.isEmpty()) {
            etPuesto.setError("El puesto es obligatorio");
            return;
        }
        if (salario.isEmpty()) {
            etSalario.setError("El salario es obligatorio");
            return;
        }


        Empleado nuevoEmpleado = new Empleado("", nombre, puesto, salario);


        db.collection("empleados")
                .add(nuevoEmpleado)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(AgregarEmpleadoActivity.this, "Empleado guardado con éxito", Toast.LENGTH_SHORT).show();

                        // se limpia los campos asi podemos agregar otro empleadosss
                        etNombre.setText("");
                        etPuesto.setText("");
                        etSalario.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(AgregarEmpleadoActivity.this, "Error al guardar: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditarEmpleadoActivity extends AppCompatActivity {

    private EditText etEditarNombre, etEditarPuesto, etEditarSalario;
    private Button btnActualizar, btnEliminar;

    private FirebaseFirestore db;
    private String idEmpleado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empleado);

        db = FirebaseFirestore.getInstance();

        etEditarNombre = findViewById(R.id.etEditarNombre);
        etEditarPuesto = findViewById(R.id.etEditarPuesto);
        etEditarSalario = findViewById(R.id.etEditarSalario);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);


        idEmpleado = getIntent().getStringExtra("EMPLEADO_ID");


        cargarDatosEmpleado();

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarEmpleado();
            }
        });


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarEmpleado();
            }
        });
    }

    private void cargarDatosEmpleado() {
        if (idEmpleado != null) {
            db.collection("empleados").document(idEmpleado).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {

                                Empleado emp = documentSnapshot.toObject(Empleado.class);
                                if (emp != null) {
                                    etEditarNombre.setText(emp.getNombre());
                                    etEditarPuesto.setText(emp.getPuesto());
                                    etEditarSalario.setText(emp.getSalario());
                                }
                            }
                        }
                    });
        }
    }

    private void actualizarEmpleado() {
        String nombre = etEditarNombre.getText().toString().trim();
        String puesto = etEditarPuesto.getText().toString().trim();
        String salario = etEditarSalario.getText().toString().trim();

        // Validaciones
        if (nombre.isEmpty() || puesto.isEmpty() || salario.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }


        Empleado empleadoActualizado = new Empleado("", nombre, puesto, salario);


        db.collection("empleados").document(idEmpleado).set(empleadoActualizado)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditarEmpleadoActivity.this, "Actualizado con éxito!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditarEmpleadoActivity.this, "Error al actualizar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void eliminarEmpleado() {
        db.collection("empleados").document(idEmpleado).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditarEmpleadoActivity.this, "Empleado eliminado!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditarEmpleadoActivity.this, "Error al eliminar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
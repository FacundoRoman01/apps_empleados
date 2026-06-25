package com.example.sistema_empleados;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    private EditText etNuevoCorreo;
    private EditText etNuevaContrasena;


    private EditText etNombreApellido;
    private EditText etTelefono;

    private Button btnConfirmarRegistro;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etNuevoCorreo = findViewById(R.id.etNuevoCorreo);
        etNuevaContrasena = findViewById(R.id.etNuevaContrasena);


        etNombreApellido = findViewById(R.id.etNombreApellido);
        etTelefono = findViewById(R.id.etTelefono);

        btnConfirmarRegistro = findViewById(R.id.btnConfirmarRegistro);

        btnConfirmarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        String correo = etNuevoCorreo.getText().toString().trim();
        String contrasena = etNuevaContrasena.getText().toString().trim();
        String nombreCompleto = etNombreApellido.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();


        if (nombreCompleto.isEmpty()) {
            etNombreApellido.setError("El nombre es obligatorio");
            return;
        }
        if (telefono.isEmpty()) {
            etTelefono.setError("El teléfono es obligatorio");
            return;
        }
        if (correo.isEmpty()) {
            etNuevoCorreo.setError("El correo es obligatorio");
            return;
        }
        if (contrasena.isEmpty() || contrasena.length() < 6) {
            etNuevaContrasena.setError("Debe tener al menos 6 caracteres");
            return;
        }


        mAuth.createUserWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String uid = user.getUid();


                                Map<String, Object> datosUsuario = new HashMap<>();
                                datosUsuario.put("nombre_completo", nombreCompleto);
                                datosUsuario.put("telefono", telefono);
                                datosUsuario.put("correo", correo);


                                db.collection("usuarios").document(uid).set(datosUsuario)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(RegistroActivity.this, "Usuario registrado con éxito!", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(RegistroActivity.this, "Error al guardar perfil: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(RegistroActivity.this, "Error al registrar credenciales. Quizás el correo ya existe.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
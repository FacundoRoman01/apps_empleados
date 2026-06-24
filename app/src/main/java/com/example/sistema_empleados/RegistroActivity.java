package com.example.sistema_empleados;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistroActivity extends AppCompatActivity {

    private EditText etNuevoCorreo;
    private EditText etNuevaContrasena;
    private Button btnConfirmarRegistro;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();

        etNuevoCorreo = findViewById(R.id.etNuevoCorreo);
        etNuevaContrasena = findViewById(R.id.etNuevaContrasena);
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


        if (correo.isEmpty()) {
            etNuevoCorreo.setError("El correo es obligatorio");
            return;
        }

        if (contrasena.isEmpty()) {
            etNuevaContrasena.setError("La contraseña es obligatoria");
            return;
        }

        if (contrasena.length() < 6) {

            etNuevaContrasena.setError("Debe tener al menos 6 caracteres");
            return;
        }


        mAuth.createUserWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(RegistroActivity.this, "Usuario registrado con éxito!", Toast.LENGTH_SHORT).show();


                            finish();
                        } else {

                            Toast.makeText(RegistroActivity.this, "Error al registrar: verifique los datos", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
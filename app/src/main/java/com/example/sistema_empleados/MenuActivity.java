package com.example.sistema_empleados;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MenuActivity extends AppCompatActivity {

    private View btnIrAgregar;
    private View btnIrLista;


    private TextView tvBienvenida;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        tvBienvenida = findViewById(R.id.tvBienvenida);

        btnIrAgregar = findViewById(R.id.btnIrAgregar);
        btnIrLista = findViewById(R.id.btnIrLista);


        cargarSaludoPersonalizado();

        // Agregar Empleadossss
        btnIrAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AgregarEmpleadoActivity.class);
                startActivity(intent);
            }
        });

        // Para ver la lista de empleadoss
        btnIrLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ListaEmpleadosActivity.class);
                startActivity(intent);
            }
        });


        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);


        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();


                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);


                finish();
            }
        });
    }


    private void cargarSaludoPersonalizado() {
        if (mAuth.getCurrentUser() != null) {
            String uidUsuario = mAuth.getCurrentUser().getUid();

            db.collection("usuarios").document(uidUsuario).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {

                                String nombre = documentSnapshot.getString("nombre_completo");

                                if (nombre != null) {

                                    tvBienvenida.setText("Bienvenido " + nombre + ",\nahora vas a poder administrar a tus empleados.");
                                }
                            }
                        }
                    });
        }
    }
}
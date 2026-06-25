package com.example.sistema_empleados;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListaEmpleadosActivity extends AppCompatActivity {

    private ListView listViewEmpleados;
    private FirebaseFirestore db;
    private List<String> listaDatosEmpleados;
    private List<String> listaIdsEmpleados;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_empleados);

        listViewEmpleados = findViewById(R.id.listViewEmpleados);


        TextView tvVacio = findViewById(R.id.tvVacio);
        listViewEmpleados.setEmptyView(tvVacio);

        db = FirebaseFirestore.getInstance();
        listaDatosEmpleados = new ArrayList<>();
        listaIdsEmpleados = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, R.layout.item_empleado, R.id.tvInfoEmpleado, listaDatosEmpleados);
        listViewEmpleados.setAdapter(adapter);

        listViewEmpleados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idFirestore = listaIdsEmpleados.get(position);

                Intent intent = new Intent(ListaEmpleadosActivity.this, EditarEmpleadoActivity.class);
                intent.putExtra("EMPLEADO_ID", idFirestore);
                startActivity(intent);
            }
        });

        obtenerEmpleados();
    }

    private void obtenerEmpleados() {

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {


            String idUsuarioActual = FirebaseAuth.getInstance().getCurrentUser().getUid();


            db.collection("empleados")
                    .whereEqualTo("id_usuario", idUsuarioActual)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                listaDatosEmpleados.clear();
                                listaIdsEmpleados.clear();

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Empleado emp = document.toObject(Empleado.class);

                                    String info = "Nombre: " + emp.getNombre() + "\n" +
                                            "Puesto: " + emp.getPuesto() + "\n" +
                                            "Salario: $" + emp.getSalario();
                                    listaDatosEmpleados.add(info);

                                    listaIdsEmpleados.add(document.getId());
                                }

                                adapter.notifyDataSetChanged();

                                if (listaDatosEmpleados.isEmpty()) {
                                    Toast.makeText(ListaEmpleadosActivity.this, "La nómina está vacía", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ListaEmpleadosActivity.this, "Error al cargar la nómina", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Error: Sesión no válida", Toast.LENGTH_SHORT).show();
        }
    }
}
package com.example.sistema_empleados;

public class Empleado {
    private String id;
    private String nombre;
    private String puesto;
    private String salario;


    public Empleado() {
    }


    public Empleado(String id, String nombre, String puesto, String salario) {
        this.id = id;
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getPuesto() { return puesto; }
    public String getSalario() { return salario; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPuesto(String puesto) { this.puesto = puesto; }
    public void setSalario(String salario) { this.salario = salario; }
}
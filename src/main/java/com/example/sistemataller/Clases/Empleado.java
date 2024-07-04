package com.example.sistemataller.Clases;

import java.util.Date;

public class Empleado {
    private String nombre;
    private String apellido;
    private int id;
    private Date fechaContratacion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public Empleado(String nombre, String apellido, int id, Date fechaContratacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        this.fechaContratacion = fechaContratacion;
    }
}


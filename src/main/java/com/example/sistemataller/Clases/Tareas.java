package com.example.sistemataller.Clases;

import java.util.Date;

public class Tareas {
    private int idTarea;
    private Categoria categoria;
    private String descripcion;
    private double precio;
    private Empleado empleado;
    private Date fechaAsignacion;

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Tareas(int idTarea, Categoria categoria, String descripcion, double precio, Empleado empleado, Date fechaAsignacion) {
        this.idTarea = idTarea;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.precio = precio;
        this.empleado = empleado;
        this.fechaAsignacion = fechaAsignacion;
    }
}

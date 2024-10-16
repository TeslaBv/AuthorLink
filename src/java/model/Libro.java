/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author CruzF
 */
public class Libro {
    private int id;
    private String titulo;
    private java.sql.Date fechaPublicacion;
    private double precio;
    private boolean disponible;
    private int cantidad;

    // Constructor vacío
    public Libro() {}

    // Constructor con parámetros
    public Libro(int id, String titulo, java.sql.Date fechaPublicacion, double precio, boolean disponible, int cantidad) {
        this.id = id;
        this.titulo = titulo;
        this.fechaPublicacion = fechaPublicacion;
        this.precio = precio;
        this.disponible = disponible;
        this.cantidad = cantidad;
    }
    
    public Libro(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public java.sql.Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(java.sql.Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Libro{" + "id=" + id + ", titulo=" + titulo + ", fechaPublicacion=" + fechaPublicacion + ", precio=" + precio + ", disponible=" + disponible + ", cantidad=" + cantidad + '}';
    }

    
    
}

package com.example.herbario.data;

public class Planta {
    private String nombre;
    private String descripcion;
    private int cantidad;
    private double precio;
    private int imagenResId; // Recurso de imagen

    public Planta(String nombre, String descripcion, int cantidad, double precio, int imagenResId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
        this.imagenResId = imagenResId;
    }

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public int getCantidad() { return cantidad; }
    public double getPrecio() { return precio; }
    public int getImagenResId() { return imagenResId; }
}
package com.example.herbario.data;

public class CartItem {
    private String nombre;
    private double precio;
    private int cantidad;
    private int imagenResId;

    public CartItem(String nombre, double precio, int cantidad, int imagenResId) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagenResId = imagenResId;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }
    public int getImagenResId() { return imagenResId; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
package com.example.herbario.data;

import java.util.List;

public class Compra {
    private String fecha;
    private List<CartItem> productos;
    private double total;

    public Compra(String fecha, List<CartItem> productos, double total) {
        this.fecha = fecha;
        this.productos = productos;
        this.total = total;
    }

    public String getFecha() { return fecha; }
    public List<CartItem> getProductos() { return productos; }
    public double getTotal() { return total; }
}
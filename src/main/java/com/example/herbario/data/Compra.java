package com.example.herbario.data;

import java.util.List;
import java.util.ArrayList;
import com.example.herbario.data.Calificacion;

public class Compra {
    private String fecha;
    private List<CartItem> productos;
    private double total;
    private List<Calificacion> calificaciones;

    public Compra(String fecha, List<CartItem> productos, double total) {
        this.fecha = fecha;
        this.productos = productos;
        this.total = total;
        this.calificaciones = new ArrayList<>();
    }

    public String getFecha() { return fecha; }
    public List<CartItem> getProductos() { return productos; }
    public double getTotal() { return total; }
    public List<Calificacion> getCalificaciones() { return calificaciones; }
    public void addCalificacion(Calificacion calificacion) { this.calificaciones.add(calificacion); }
}
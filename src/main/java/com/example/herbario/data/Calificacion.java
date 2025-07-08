package com.example.herbario.data;

public class Calificacion {
    private String producto; // nombre del producto
    private String usuario; // nombre del usuario
    private int estrellas;
    private String comentario;

    public Calificacion(String producto, String usuario, float estrellas, String comentario) {
        this.producto = producto;
        this.usuario = usuario;
        this.estrellas = (int) estrellas;
        this.comentario = comentario;
    }

    public int getEstrellas() { return estrellas; }
    public String getComentario() { return comentario; }
    public String getProducto() { return producto; }
    public String getUsuario() { return usuario; }
}
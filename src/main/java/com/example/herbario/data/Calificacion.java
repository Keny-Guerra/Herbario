package com.example.herbario.data;

public class Calificacion {
    private String producto;
    private String usuario;
    private float estrellas;
    private String comentario;

    public Calificacion(String producto, String usuario, float estrellas, String comentario) {
        this.producto = producto;
        this.usuario = usuario;
        this.estrellas = estrellas;
        this.comentario = comentario;
    }

    public String getProducto() { return producto; }
    public String getUsuario() { return usuario; }
    public float getEstrellas() { return estrellas; }
    public String getComentario() { return comentario; }
}
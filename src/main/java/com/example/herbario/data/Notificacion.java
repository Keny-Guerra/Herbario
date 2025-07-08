package com.example.herbario.data;

public class Notificacion {
    private int id;
    private String usuarioEmail;
    private String titulo;
    private String mensaje;
    private String fecha;
    private boolean mostrada;

    public Notificacion(int id, String usuarioEmail, String titulo, String mensaje, String fecha, boolean mostrada) {
        this.id = id;
        this.usuarioEmail = usuarioEmail;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.mostrada = mostrada;
    }

    public int getId() { return id; }
    public String getUsuarioEmail() { return usuarioEmail; }
    public String getTitulo() { return titulo; }
    public String getMensaje() { return mensaje; }
    public String getFecha() { return fecha; }
    public boolean isMostrada() { return mostrada; }
}
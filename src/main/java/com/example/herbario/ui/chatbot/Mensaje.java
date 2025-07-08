package com.example.herbario.ui.chatbot;

public class Mensaje {
    private String texto;
    private boolean esUsuario;

    public Mensaje(String texto, boolean esUsuario) {
        this.texto = texto;
        this.esUsuario = esUsuario;
    }

    public String getTexto() { return texto; }
    public boolean esUsuario() { return esUsuario; }
}
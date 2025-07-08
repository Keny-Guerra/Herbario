package com.example.herbario.ui.chatbot;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.herbario.R;
import java.util.ArrayList;
import java.util.List;

public class ChatbotActivity extends AppCompatActivity {
    private List<Mensaje> mensajes = new ArrayList<>();
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(mensajes);
        recyclerView.setAdapter(adapter);

        EditText editMensaje = findViewById(R.id.editMensaje);
        ImageButton btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(v -> enviarMensaje(editMensaje, recyclerView));
        editMensaje.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                enviarMensaje(editMensaje, recyclerView);
                return true;
            }
            return false;
        });
    }

    private void enviarMensaje(EditText editMensaje, RecyclerView recyclerView) {
        String texto = editMensaje.getText().toString().trim();
        if (!texto.isEmpty()) {
            mensajes.add(new Mensaje(texto, true));
            adapter.notifyItemInserted(mensajes.size() - 1);
            recyclerView.scrollToPosition(mensajes.size() - 1);
            editMensaje.setText("");
            responderComoBot(texto, recyclerView);
        }
    }

    private void responderComoBot(String textoUsuario, RecyclerView recyclerView) {
        // Respuesta simple, puedes mejorar la lógica aquí
        String respuesta = obtenerRespuestaBot(textoUsuario);
        mensajes.add(new Mensaje(respuesta, false));
        adapter.notifyItemInserted(mensajes.size() - 1);
        recyclerView.scrollToPosition(mensajes.size() - 1);
    }

    private String obtenerRespuestaBot(String textoUsuario) {
        textoUsuario = textoUsuario.toLowerCase();
        if (textoUsuario.contains("hola")) return "¡Hola! ¿En qué puedo ayudarte con tus plantas medicinales?";
        if (textoUsuario.contains("comprar")) return "Puedes comprar plantas desde el catálogo y agregarlas al carrito.";
        if (textoUsuario.contains("recomienda")) return "Te recomiendo la manzanilla para dolores estomacales y la lavanda para relajarte.";
        if (textoUsuario.contains("gracias")) return "¡De nada! Si tienes más preguntas, aquí estaré.";
        return "Lo siento, aún estoy aprendiendo. ¿Puedes preguntar de otra forma?";
    }
}
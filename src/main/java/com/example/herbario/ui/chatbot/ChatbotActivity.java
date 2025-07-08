package com.example.herbario.ui.chatbot;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.herbario.R;
import com.example.herbario.data.ChatManager;
import java.util.List;

public class ChatbotActivity extends AppCompatActivity {
    private List<Mensaje> mensajes;
    private ChatAdapter adapter;
    private ChatManager chatManager;
    private String usuarioEmail = "usuario@ejemplo.com"; // TODO: obtener email real del usuario logueado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        chatManager = new ChatManager(this);
        mensajes = chatManager.obtenerHistorialChat(usuarioEmail);

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
            // Guardar mensaje del usuario
            chatManager.guardarMensaje(texto, true, usuarioEmail);
            mensajes.add(new Mensaje(texto, true));
            adapter.notifyItemInserted(mensajes.size() - 1);
            recyclerView.scrollToPosition(mensajes.size() - 1);
            editMensaje.setText("");
            
            // Obtener y guardar respuesta del bot
            String respuesta = chatManager.obtenerRespuestaBot(texto);
            chatManager.guardarMensaje(respuesta, false, usuarioEmail);
            mensajes.add(new Mensaje(respuesta, false));
            adapter.notifyItemInserted(mensajes.size() - 1);
            recyclerView.scrollToPosition(mensajes.size() - 1);
        }
    }
}
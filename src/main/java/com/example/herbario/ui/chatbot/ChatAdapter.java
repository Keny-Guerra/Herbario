package com.example.herbario.ui.chatbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.herbario.R;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TIPO_USUARIO = 0;
    private static final int TIPO_BOT = 1;
    private List<Mensaje> mensajes;

    public ChatAdapter(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    @Override
    public int getItemViewType(int position) {
        return mensajes.get(position).esUsuario() ? TIPO_USUARIO : TIPO_BOT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TIPO_USUARIO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensaje_usuario, parent, false);
            return new UsuarioViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensaje_bot, parent, false);
            return new BotViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Mensaje mensaje = mensajes.get(position);
        if (holder instanceof UsuarioViewHolder) {
            ((UsuarioViewHolder) holder).mensaje.setText(mensaje.getTexto());
        } else if (holder instanceof BotViewHolder) {
            ((BotViewHolder) holder).mensaje.setText(mensaje.getTexto());
        }
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView mensaje;
        UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            mensaje = itemView.findViewById(R.id.textMensajeUsuario);
        }
    }

    static class BotViewHolder extends RecyclerView.ViewHolder {
        TextView mensaje;
        BotViewHolder(@NonNull View itemView) {
            super(itemView);
            mensaje = itemView.findViewById(R.id.textMensajeBot);
        }
    }
}
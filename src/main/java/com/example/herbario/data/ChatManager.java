package com.example.herbario.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import java.util.ArrayList;
import java.util.List;
import com.example.herbario.ui.chatbot.Mensaje;

public class ChatManager {
    private HerbarioDbHelper dbHelper;

    public ChatManager(Context context) {
        dbHelper = new HerbarioDbHelper(context);
    }

    public void guardarMensaje(String texto, boolean esUsuario, String usuarioEmail) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("texto", texto);
        values.put("esUsuario", esUsuario ? 1 : 0);
        values.put("usuarioEmail", usuarioEmail);
        values.put("timestamp", System.currentTimeMillis());
        db.insert("chat", null, values);
    }

    public List<Mensaje> obtenerHistorialChat(String usuarioEmail) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Mensaje> mensajes = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT texto, esUsuario FROM chat WHERE usuarioEmail=? ORDER BY timestamp ASC", new String[]{usuarioEmail});
        while (cursor.moveToNext()) {
            mensajes.add(new Mensaje(
                    cursor.getString(0), // texto
                    cursor.getInt(1) == 1 // esUsuario
            ));
        }
        cursor.close();
        return mensajes;
    }

    public void limpiarHistorialChat(String usuarioEmail) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("chat", "usuarioEmail=?", new String[]{usuarioEmail});
    }

    public String obtenerRespuestaBot(String textoUsuario) {
        textoUsuario = textoUsuario.toLowerCase();
        
        // Respuestas más inteligentes y específicas para plantas medicinales
        if (textoUsuario.contains("hola") || textoUsuario.contains("buenos días") || textoUsuario.contains("buenas")) {
            return "¡Hola! Soy tu asistente de plantas medicinales. ¿En qué puedo ayudarte hoy?";
        }
        
        if (textoUsuario.contains("comprar") || textoUsuario.contains("precio") || textoUsuario.contains("costo")) {
            return "Puedes ver todos nuestros productos en el catálogo. Los precios van desde $4.50 hasta $7.00. ¿Te gustaría que te recomiende alguna planta específica?";
        }
        
        if (textoUsuario.contains("recomienda") || textoUsuario.contains("recomendación")) {
            return "Te recomiendo:\n• Manzanilla: Para dolores estomacales y relajación\n• Menta: Refrescante y digestiva\n• Romero: Aromática y medicinal\n• Lavanda: Relajante y para el estrés";
        }
        
        if (textoUsuario.contains("dolor") || textoUsuario.contains("estómago") || textoUsuario.contains("digestión")) {
            return "Para problemas digestivos te recomiendo la manzanilla o la menta. Ambas son excelentes para calmar dolores estomacales y mejorar la digestión.";
        }
        
        if (textoUsuario.contains("estrés") || textoUsuario.contains("ansiedad") || textoUsuario.contains("relaj")) {
            return "Para relajación y reducir el estrés, la lavanda es perfecta. También puedes probar la manzanilla que tiene propiedades calmantes.";
        }
        
        if (textoUsuario.contains("carrito") || textoUsuario.contains("agregar")) {
            return "Para agregar productos al carrito, ve al catálogo, selecciona la planta que te guste y toca en ella. Se agregará automáticamente al carrito.";
        }
        
        if (textoUsuario.contains("gracias") || textoUsuario.contains("thank")) {
            return "¡De nada! Me alegra poder ayudarte. Si tienes más preguntas sobre plantas medicinales, aquí estaré.";
        }
        
        if (textoUsuario.contains("adiós") || textoUsuario.contains("chao") || textoUsuario.contains("hasta luego")) {
            return "¡Hasta luego! Que tengas un excelente día. Recuerda que aquí estaré para ayudarte con tus plantas medicinales.";
        }
        
        return "Entiendo tu pregunta. Como asistente de plantas medicinales, puedo ayudarte con:\n• Recomendaciones de plantas\n• Información sobre precios\n• Cómo comprar productos\n• Propiedades medicinales\n¿En qué te gustaría que te ayude?";
    }
} 
package com.example.herbario.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import java.util.ArrayList;
import java.util.List;

public class NotificacionManager {
    private HerbarioDbHelper dbHelper;

    public NotificacionManager(Context context) {
        dbHelper = new HerbarioDbHelper(context);
    }

    // Guardar una notificación
    public void guardarNotificacion(String usuarioEmail, String titulo, String mensaje, String fecha) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("usuarioEmail", usuarioEmail);
        values.put("titulo", titulo);
        values.put("mensaje", mensaje);
        values.put("fecha", fecha);
        values.put("mostrada", 0);
        db.insert("notificaciones", null, values);
    }

    // Obtener notificaciones pendientes para mostrar
    public List<Notificacion> obtenerNotificacionesPendientes(String usuarioEmail, String fechaActual) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Notificacion> notificaciones = new ArrayList<>();
        Cursor cursor = db.rawQuery(
                "SELECT id, usuarioEmail, titulo, mensaje, fecha, mostrada FROM notificaciones WHERE usuarioEmail=? AND fecha<=? AND mostrada=0",
                new String[]{usuarioEmail, fechaActual}
        );
        while (cursor.moveToNext()) {
            notificaciones.add(new Notificacion(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5) == 1
            ));
        }
        cursor.close();
        return notificaciones;
    }

    // Marcar notificación como mostrada
    public void marcarComoMostrada(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mostrada", 1);
        db.update("notificaciones", values, "id=?", new String[]{String.valueOf(id)});
    }
}
package com.example.herbario.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import java.util.ArrayList;
import java.util.List;

public class CalificacionManager {
    private HerbarioDbHelper dbHelper;

    public CalificacionManager(Context context) {
        dbHelper = new HerbarioDbHelper(context);
    }

    // Guardar calificación para un producto o compra
    public void guardarCalificacion(Integer compraId, Integer productoId, String usuarioEmail, int estrellas, String comentario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (compraId != null) values.put("compraId", compraId);
        if (productoId != null) values.put("productoId", productoId);
        values.put("usuarioEmail", usuarioEmail);
        values.put("estrellas", estrellas);
        values.put("comentario", comentario);
        db.insert("calificaciones", null, values);
    }

    // Método simplificado para RatingActivity
    public void guardarCalificacion(String usuarioEmail, String producto, float estrellas, String comentario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("productoId", 0); // Usar 0 como placeholder para productoId
        values.put("usuarioEmail", usuarioEmail);
        values.put("estrellas", (int) estrellas);
        values.put("comentario", comentario);
        db.insert("calificaciones", null, values);
    }

    // Obtener todas las calificaciones
    public List<Calificacion> obtenerTodasLasCalificaciones() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Calificacion> calificaciones = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT productoId, usuarioEmail, estrellas, comentario FROM calificaciones ORDER BY id DESC", null);
        while (cursor.moveToNext()) {
            calificaciones.add(new Calificacion(
                    String.valueOf(cursor.getInt(0)),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3)
            ));
        }
        cursor.close();
        return calificaciones;
    }

    // Obtener calificaciones de un producto
    public List<Calificacion> obtenerCalificacionesProducto(int productoId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Calificacion> calificaciones = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT usuarioEmail, estrellas, comentario FROM calificaciones WHERE productoId=?", new String[]{String.valueOf(productoId)});
        while (cursor.moveToNext()) {
            calificaciones.add(new Calificacion(
                    String.valueOf(productoId),
                    cursor.getString(0),
                    cursor.getInt(1),
                    cursor.getString(2)
            ));
        }
        cursor.close();
        return calificaciones;
    }

    // Obtener calificaciones de una compra
    public List<Calificacion> obtenerCalificacionesCompra(int compraId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Calificacion> calificaciones = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT productoId, usuarioEmail, estrellas, comentario FROM calificaciones WHERE compraId=?", new String[]{String.valueOf(compraId)});
        while (cursor.moveToNext()) {
            calificaciones.add(new Calificacion(
                    String.valueOf(cursor.getInt(0)),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3)
            ));
        }
        cursor.close();
        return calificaciones;
    }
}
package com.example.herbario.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import java.util.ArrayList;
import java.util.List;

public class CarritoManager {
    private HerbarioDbHelper dbHelper;

    public CarritoManager(Context context) {
        dbHelper = new HerbarioDbHelper(context);
    }

    public void agregarAlCarrito(String usuarioEmail, int productoId, String nombre, double precio, int cantidad, int imagenResId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        
        // Verificar si el producto ya está en el carrito
        Cursor cursor = db.rawQuery("SELECT id, cantidad FROM carrito WHERE usuarioEmail=? AND productoId=?", 
                                   new String[]{usuarioEmail, String.valueOf(productoId)});
        
        if (cursor.moveToFirst()) {
            // Producto ya existe, actualizar cantidad
            int idCarrito = cursor.getInt(0);
            int cantidadActual = cursor.getInt(1);
            int nuevaCantidad = cantidadActual + cantidad;
            
            ContentValues values = new ContentValues();
            values.put("cantidad", nuevaCantidad);
            db.update("carrito", values, "id = ?", new String[]{String.valueOf(idCarrito)});
        } else {
            // Producto nuevo, agregar al carrito
            ContentValues values = new ContentValues();
            values.put("usuarioEmail", usuarioEmail);
            values.put("productoId", productoId);
            values.put("nombre", nombre);
            values.put("precio", precio);
            values.put("cantidad", cantidad);
            values.put("imagenResId", imagenResId);
            db.insert("carrito", null, values);
        }
        cursor.close();
    }

    public List<CartItem> obtenerCarrito(String usuarioEmail) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<CartItem> carrito = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT productoId, nombre, precio, cantidad, imagenResId FROM carrito WHERE usuarioEmail=?", 
                                   new String[]{usuarioEmail});
        while (cursor.moveToNext()) {
            carrito.add(new CartItem(
                    cursor.getString(1), // nombre
                    cursor.getDouble(2), // precio
                    cursor.getInt(3),    // cantidad
                    cursor.getInt(4)     // imagenResId
            ));
        }
        cursor.close();
        return carrito;
    }

    public void actualizarCantidad(String usuarioEmail, int productoId, int nuevaCantidad) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cantidad", nuevaCantidad);
        db.update("carrito", values, "usuarioEmail=? AND productoId=?", 
                 new String[]{usuarioEmail, String.valueOf(productoId)});
    }

    public void eliminarProductoDelCarrito(String usuarioEmail, int productoId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("carrito", "usuarioEmail=? AND productoId=?", 
                 new String[]{usuarioEmail, String.valueOf(productoId)});
    }

    public void limpiarCarrito(String usuarioEmail) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("carrito", "usuarioEmail=?", new String[]{usuarioEmail});
    }
}
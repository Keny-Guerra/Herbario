package com.example.herbario.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class CompraManager {
    private HerbarioDbHelper dbHelper;
    private static final String TAG = "CompraManager";

    public CompraManager(Context context) {
        dbHelper = new HerbarioDbHelper(context);
    }

    // Guardar una compra y su detalle
    public boolean guardarCompra(String usuarioEmail, String fecha, double total, List<CartItem> productos) {
        if (productos == null || productos.isEmpty()) {
            Log.e(TAG, "Error: Lista de productos vacía o nula");
            return false;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            Log.d(TAG, "Iniciando guardado de compra para usuario: " + usuarioEmail);
            Log.d(TAG, "Fecha: " + fecha + ", Total: " + total + ", Productos: " + productos.size());

            ContentValues values = new ContentValues();
            values.put("usuarioEmail", usuarioEmail);
            values.put("fecha", fecha);
            values.put("total", total);
            long compraId = db.insert("compras", null, values);

            if (compraId != -1) {
                Log.d(TAG, "Compra guardada con ID: " + compraId);
                int detallesGuardados = 0;
                
                for (CartItem item : productos) {
                    ContentValues detalle = new ContentValues();
                    detalle.put("compraId", compraId);
                    detalle.put("productoId", 0);
                    detalle.put("nombre", item.getNombre());
                    detalle.put("precio", item.getPrecio());
                    detalle.put("cantidad", item.getCantidad());
                    detalle.put("imagenResId", item.getImagenResId());
                    
                    long detalleId = db.insert("compras_detalle", null, detalle);
                    if (detalleId != -1) {
                        detallesGuardados++;
                        Log.d(TAG, "Detalle guardado con ID: " + detalleId + " para producto: " + item.getNombre());
                    } else {
                        Log.e(TAG, "Error al guardar detalle para producto: " + item.getNombre());
                    }
                }
                
                Log.d(TAG, "Detalles guardados: " + detallesGuardados + " de " + productos.size());
                
                if (detallesGuardados == productos.size()) {
                    db.setTransactionSuccessful();
                    Log.d(TAG, "Transacción completada exitosamente");
                    return true;
                } else {
                    Log.e(TAG, "No se pudieron guardar todos los detalles");
                    return false;
                }
            } else {
                Log.e(TAG, "Error al insertar compra");
                return false;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error en guardarCompra: " + e.getMessage(), e);
            return false;
        } finally {
            db.endTransaction();
        }
    }

    // Obtener historial de compras de un usuario
    public List<Compra> obtenerHistorial(String usuarioEmail) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Compra> historial = new ArrayList<>();
        
        Log.d(TAG, "Obteniendo historial para usuario: " + usuarioEmail);
        
        String query = "SELECT id, fecha, total FROM compras WHERE usuarioEmail=? ORDER BY fecha DESC";
        Cursor cursor = db.rawQuery(query, new String[]{usuarioEmail});
        
        Log.d(TAG, "Compras encontradas en BD: " + cursor.getCount());
        
        while (cursor.moveToNext()) {
            int compraId = cursor.getInt(0);
            String fecha = cursor.getString(1);
            double total = cursor.getDouble(2);
            List<CartItem> productos = obtenerDetalleCompra(compraId);
            historial.add(new Compra(fecha, productos, total));
            Log.d(TAG, "Compra cargada - ID: " + compraId + ", Fecha: " + fecha + ", Total: " + total + ", Productos: " + productos.size());
        }
        cursor.close();
        
        Log.d(TAG, "Historial obtenido con " + historial.size() + " compras");
        return historial;
    }

    // Obtener productos de una compra
    private List<CartItem> obtenerDetalleCompra(int compraId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<CartItem> productos = new ArrayList<>();
        
        String query = "SELECT nombre, precio, cantidad, imagenResId FROM compras_detalle WHERE compraId=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(compraId)});
        
        while (cursor.moveToNext()) {
            productos.add(new CartItem(
                    cursor.getString(0), // nombre
                    cursor.getDouble(1), // precio
                    cursor.getInt(2),    // cantidad
                    cursor.getInt(3)     // imagenResId
            ));
        }
        cursor.close();
        return productos;
    }

    // Método de depuración para verificar todas las compras
    public void verificarCompras() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        
        Cursor cursor = db.rawQuery("SELECT * FROM compras", null);
        Log.d(TAG, "=== VERIFICACIÓN DE BASE DE DATOS ===");
        Log.d(TAG, "Total de compras en BD: " + cursor.getCount());
        
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(0);
                String email = cursor.getString(1);
                String fecha = cursor.getString(2);
                double total = cursor.getDouble(3);
                Log.d(TAG, "Compra ID: " + id + ", Usuario: " + email + ", Fecha: " + fecha + ", Total: " + total);
            } while (cursor.moveToNext());
        }
        cursor.close();
        
        Cursor cursorDetalle = db.rawQuery("SELECT * FROM compras_detalle", null);
        Log.d(TAG, "Total de detalles en BD: " + cursorDetalle.getCount());
        
        if (cursorDetalle.getCount() > 0) {
            cursorDetalle.moveToFirst();
            do {
                int id = cursorDetalle.getInt(0);
                int compraId = cursorDetalle.getInt(1);
                String nombre = cursorDetalle.getString(3);
                double precio = cursorDetalle.getDouble(4);
                int cantidad = cursorDetalle.getInt(5);
                Log.d(TAG, "Detalle ID: " + id + ", CompraID: " + compraId + ", Producto: " + nombre + ", Precio: " + precio + ", Cantidad: " + cantidad);
            } while (cursorDetalle.moveToNext());
        }
        cursorDetalle.close();
        Log.d(TAG, "=== FIN VERIFICACIÓN ===");
    }
}
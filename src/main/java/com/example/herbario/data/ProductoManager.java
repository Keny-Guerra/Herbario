package com.example.herbario.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import java.util.ArrayList;
import java.util.List;
import com.example.herbario.R;

public class ProductoManager {
    private HerbarioDbHelper dbHelper;

    public ProductoManager(Context context) {
        dbHelper = new HerbarioDbHelper(context);
    }

    public void agregarProducto(String nombre, String descripcion, int stock, double precio, int imagenResId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("stock", stock);
        values.put("precio", precio);
        values.put("imagenResId", imagenResId);
        db.insert("productos", null, values);
    }

    public List<Planta> obtenerTodosLosProductos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Planta> productos = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT nombre, descripcion, stock, precio, imagenResId FROM productos ORDER BY nombre", null);
        while (cursor.moveToNext()) {
            productos.add(new Planta(
                    cursor.getString(0), // nombre
                    cursor.getString(1), // descripcion
                    cursor.getInt(2),    // stock
                    cursor.getDouble(3), // precio
                    cursor.getInt(4)     // imagenResId
            ));
        }
        cursor.close();
        return productos;
    }

    public Planta obtenerProductoPorId(int productoId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nombre, descripcion, stock, precio, imagenResId FROM productos WHERE id = ?", 
                                   new String[]{String.valueOf(productoId)});
        Planta producto = null;
        if (cursor.moveToFirst()) {
            producto = new Planta(
                    cursor.getString(0), // nombre
                    cursor.getString(1), // descripcion
                    cursor.getInt(2),    // stock
                    cursor.getDouble(3), // precio
                    cursor.getInt(4)     // imagenResId
            );
        }
        cursor.close();
        return producto;
    }

    public List<Planta> buscarProductos(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return obtenerTodosLosProductos();
        }
        
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Planta> productos = new ArrayList<>();
        String query = "SELECT nombre, descripcion, stock, precio, imagenResId FROM productos WHERE LOWER(nombre) LIKE LOWER(?) OR LOWER(descripcion) LIKE LOWER(?) ORDER BY nombre";
        String[] args = {"%" + texto.trim() + "%", "%" + texto.trim() + "%"};
        Cursor cursor = db.rawQuery(query, args);
        while (cursor.moveToNext()) {
            productos.add(new Planta(
                    cursor.getString(0), // nombre
                    cursor.getString(1), // descripcion
                    cursor.getInt(2),    // stock
                    cursor.getDouble(3), // precio
                    cursor.getInt(4)     // imagenResId
            ));
        }
        cursor.close();
        return productos;
    }

    public void actualizarStock(int productoId, int nuevaCantidad) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("stock", nuevaCantidad);
        db.update("productos", values, "id = ?", new String[]{String.valueOf(productoId)});
    }

    public void inicializarProductosEjemplo() {
        // Solo agregar productos si la tabla está vacía
        if (obtenerTodosLosProductos().isEmpty()) {
            // Usar los IDs correctos de los drawables
            agregarProducto("Manzanilla", "Alivia dolores estomacales y ayuda con la digestión", 10, 5.0, R.drawable.ic_launcher_foreground);
            agregarProducto("Menta", "Refrescante y digestiva, ideal para problemas estomacales", 15, 4.5, R.drawable.ic_launcher_foreground);
            agregarProducto("Romero", "Aromática y medicinal, mejora la memoria y concentración", 8, 6.0, R.drawable.ic_launcher_foreground);
            agregarProducto("Lavanda", "Relajante y aromática, ayuda con el estrés y el insomnio", 12, 7.0, R.drawable.ic_launcher_foreground);
            agregarProducto("Aloe Vera", "Cicatrizante natural y regenerador de la piel", 20, 8.5, R.drawable.ic_launcher_foreground);
            agregarProducto("Eucalipto", "Expectorante natural para problemas respiratorios", 14, 6.5, R.drawable.ic_launcher_foreground);
        }
    }
} 
package com.example.herbario.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HerbarioDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "herbario.db";
    public static final int DATABASE_VERSION = 3;

    public HerbarioDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabla de usuarios
        db.execSQL("CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT UNIQUE," +
                "password TEXT," +
                "nombre TEXT)");

        // Tabla de plantas
        db.execSQL("CREATE TABLE plantas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "descripcion TEXT," +
                "cantidad INTEGER," +
                "precio REAL," +
                "imagenResId INTEGER)");

        // Tabla de productos (para ProductoManager)
        db.execSQL("CREATE TABLE productos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "descripcion TEXT," +
                "stock INTEGER," +
                "precio REAL," +
                "imagenResId INTEGER)");

        // Tabla de compras
        db.execSQL("CREATE TABLE compras (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "usuarioEmail TEXT," +
                "fecha TEXT," +
                "total REAL)");

        // Tabla Compra detalle
        db.execSQL("CREATE TABLE compras_detalle (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "compraId INTEGER," +
                "productoId INTEGER," +
                "nombre TEXT," +
                "precio REAL," +
                "cantidad INTEGER," +
                "imagenResId INTEGER)");

        // Tabla de calificaciones
        db.execSQL("CREATE TABLE calificaciones (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "compraId INTEGER," +
                "productoId INTEGER," +
                "usuarioEmail TEXT," +
                "estrellas INTEGER," +
                "comentario TEXT)");

        // Tabla de Carritos
        db.execSQL("CREATE TABLE carrito (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "usuarioEmail TEXT," +
                "productoId INTEGER," +
                "nombre TEXT," +
                "precio REAL," +
                "cantidad INTEGER," +
                "imagenResId INTEGER)");

        // Tabla de Notificacion
        db.execSQL("CREATE TABLE notificaciones (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "usuarioEmail TEXT," +
                "titulo TEXT," +
                "mensaje TEXT," +
                "fecha TEXT," +
                "mostrada INTEGER)");

        // Tabla de Chat
        db.execSQL("CREATE TABLE chat (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "usuarioEmail TEXT," +
                "texto TEXT," +
                "esUsuario INTEGER," +
                "timestamp INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Solo agregar tablas faltantes, no eliminar datos existentes
        if (oldVersion < 2) {
            // Crear tabla productos si no existe
            db.execSQL("CREATE TABLE IF NOT EXISTS productos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT," +
                    "descripcion TEXT," +
                    "stock INTEGER," +
                    "precio REAL," +
                    "imagenResId INTEGER)");
            
            // Crear tabla chat si no existe
            db.execSQL("CREATE TABLE IF NOT EXISTS chat (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "usuarioEmail TEXT," +
                    "texto TEXT," +
                    "esUsuario INTEGER," +
                    "timestamp INTEGER)");
        }
        
        // Para la versión 3, solo agregar índices para mejorar el rendimiento
        if (oldVersion < 3) {
            // Agregar índices para mejorar las consultas
            db.execSQL("CREATE INDEX IF NOT EXISTS idx_compras_usuario ON compras(usuarioEmail)");
            db.execSQL("CREATE INDEX IF NOT EXISTS idx_compras_detalle_compra ON compras_detalle(compraId)");
            db.execSQL("CREATE INDEX IF NOT EXISTS idx_productos_nombre ON productos(nombre)");
            db.execSQL("CREATE INDEX IF NOT EXISTS idx_carrito_usuario ON carrito(usuarioEmail)");
        }
    }
}
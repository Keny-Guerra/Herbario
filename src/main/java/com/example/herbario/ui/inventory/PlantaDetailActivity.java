package com.example.herbario.ui.inventory;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.herbario.R;

public class PlantaDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planta_detail);

        TextView nombre = findViewById(R.id.textDetalleNombre);
        TextView descripcion = findViewById(R.id.textDetalleDescripcion);
        TextView cantidad = findViewById(R.id.textDetalleCantidad);
        TextView precio = findViewById(R.id.textDetallePrecio);

        nombre.setText(getIntent().getStringExtra("nombre"));
        descripcion.setText(getIntent().getStringExtra("descripcion"));
        cantidad.setText("Cantidad disponible: " + getIntent().getIntExtra("stock", 0));
        precio.setText("Precio: $" + String.format("%.2f", getIntent().getDoubleExtra("precio", 0.0)));
    }
}
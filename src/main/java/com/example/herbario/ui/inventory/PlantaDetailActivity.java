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

        TextView nombre = findViewById(R.id.textNombreDetalle);
        TextView descripcion = findViewById(R.id.textDescripcionDetalle);
        TextView cantidad = findViewById(R.id.textCantidadDetalle);
        TextView precio = findViewById(R.id.textPrecioDetalle);

        nombre.setText(getIntent().getStringExtra("nombre"));
        descripcion.setText(getIntent().getStringExtra("descripcion"));
        cantidad.setText("Cantidad: " + getIntent().getIntExtra("cantidad", 0));
        precio.setText("Precio: $" + String.format("%.2f", getIntent().getDoubleExtra("precio", 0.0)));
    }
}
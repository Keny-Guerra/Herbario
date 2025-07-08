package com.example.herbario.ui.detail;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.herbario.R;

public class PlantaDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planta_detail);

        ImageView imagePlanta = findViewById(R.id.imageDetallePlanta);
        TextView nombre = findViewById(R.id.textDetalleNombre);
        TextView descripcion = findViewById(R.id.textDetalleDescripcion);
        TextView precio = findViewById(R.id.textDetallePrecio);
        TextView cantidad = findViewById(R.id.textDetalleCantidad);
        Button btnAgregarCarrito = findViewById(R.id.btnAgregarCarrito);
        Button btnRegresar = findViewById(R.id.btnRegresar);

        // Obtener datos de la planta
        String nombrePlanta = getIntent().getStringExtra("nombre");
        String descripcionPlanta = getIntent().getStringExtra("descripcion");
        double precioPlanta = getIntent().getDoubleExtra("precio", 0.0);
        int imagenResId = getIntent().getIntExtra("imagenResId", R.drawable.ic_launcher_foreground);
        int stock = getIntent().getIntExtra("stock", 0);

        imagePlanta.setImageResource(imagenResId);
        nombre.setText(nombrePlanta);
        descripcion.setText(descripcionPlanta);
        precio.setText("Precio: $" + String.format("%.2f", precioPlanta));
        cantidad.setText("Cantidad disponible: " + stock);

        btnAgregarCarrito.setOnClickListener(v -> {
            // Aquí puedes agregar la lógica para agregar al carrito
        });
        btnRegresar.setOnClickListener(v -> finish());
    }
}
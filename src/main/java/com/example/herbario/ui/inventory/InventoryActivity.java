package com.example.herbario.ui.inventory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.herbario.R;
import com.example.herbario.data.Planta;
import com.example.herbario.data.CartItem;
import com.example.herbario.data.ProductoManager;
import com.example.herbario.data.CarritoManager;
import com.example.herbario.ui.chatbot.ChatbotActivity;
import com.example.herbario.ui.preferences.PreferencesActivity;
import com.example.herbario.ui.cart.CartActivity;
import java.util.List;
import android.widget.ImageButton;

public class InventoryActivity extends AppCompatActivity implements InventoryAdapter.OnPlantaClickListener {
    private List<Planta> plantas;
    private InventoryAdapter adapter;
    private ProductoManager productoManager;
    private CarritoManager carritoManager;
    private String usuarioEmail = "usuario@ejemplo.com"; // TODO: obtener email real del usuario logueado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        productoManager = new ProductoManager(this);
        carritoManager = new CarritoManager(this);
        productoManager.inicializarProductosEjemplo(); // Solo si está vacío
        plantas = productoManager.obtenerTodosLosProductos();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewInventory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InventoryAdapter(plantas, this);
        recyclerView.setAdapter(adapter);

        Button btnPreferencias = findViewById(R.id.btnPreferencias);
        Button btnChatbot = findViewById(R.id.btnChatbot);
        ImageButton btnCarrito = findViewById(R.id.btnCarrito);

        btnPreferencias.setOnClickListener(v -> {
            startActivity(new Intent(this, PreferencesActivity.class));
        });

        btnChatbot.setOnClickListener(v -> {
            startActivity(new Intent(this, ChatbotActivity.class));
        });

        btnCarrito.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
        });
    }

    @Override
    public void onPlantaClick(Planta planta) {
        Intent intent = new Intent(this, PlantaDetailActivity.class);
        intent.putExtra("nombre", planta.getNombre());
        intent.putExtra("descripcion", planta.getDescripcion());
        intent.putExtra("cantidad", planta.getCantidad());
        intent.putExtra("precio", planta.getPrecio());
        startActivity(intent);

        // Agregar al carrito usando CarritoManager
        carritoManager.agregarAlCarrito(
            usuarioEmail,
            0, // productoId - usar 0 como placeholder
            planta.getNombre(),
            planta.getPrecio(),
            1,
            planta.getImagenResId()
        );
        Toast.makeText(this, "Agregado al carrito", Toast.LENGTH_SHORT).show();
    }
}
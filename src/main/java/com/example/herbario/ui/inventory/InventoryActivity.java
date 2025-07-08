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
import com.example.herbario.ui.chatbot.ChatbotActivity;
import com.example.herbario.ui.preferences.PreferencesActivity;
import com.example.herbario.ui.cart.CartActivity;
import java.util.List;
import java.util.ArrayList;
import android.widget.ImageButton;

public class InventoryActivity extends AppCompatActivity implements InventoryAdapter.OnPlantaClickListener {
    private List<Planta> plantas;
    private InventoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        plantas = getPlantasEjemplo();

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

    private List<Planta> getPlantasEjemplo() {
        List<Planta> lista = new ArrayList<>();
        lista.add(new Planta("Manzanilla", "Alivia dolores estomacales", 10, 5.0, R.drawable.manzanilla));
        lista.add(new Planta("Menta", "Refrescante y digestiva", 15, 4.5, R.drawable.menta));
        lista.add(new Planta("Romero", "Aromática y medicinal", 8, 6.0, R.drawable.romero));
        lista.add(new Planta("Lavanda", "Relajante y aromática", 12, 7.0, R.drawable.lavanda));
        return lista;
    }

    @Override
    public void onPlantaClick(Planta planta) {
        Intent intent = new Intent(this, PlantaDetailActivity.class);
        intent.putExtra("nombre", planta.getNombre());
        intent.putExtra("descripcion", planta.getDescripcion());
        intent.putExtra("cantidad", planta.getCantidad());
        intent.putExtra("precio", planta.getPrecio());
        startActivity(intent);

        CartActivity.cartItems.add(new CartItem(
            planta.getNombre(),
            planta.getPrecio(),
            1,
            planta.getImagenResId()
        ));
        Toast.makeText(this, "Agregado al carrito", Toast.LENGTH_SHORT).show();
    }
}
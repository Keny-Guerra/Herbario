package com.example.herbario.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.herbario.R;
import com.example.herbario.data.CartItem;
import com.example.herbario.data.CarritoManager;
import com.example.herbario.ui.checkout.CheckoutActivity;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartChangeListener {
    private CartAdapter adapter;
    private TextView totalText;
    private CarritoManager carritoManager;
    private List<CartItem> cartItems;
    private String usuarioEmail = "usuario@ejemplo.com"; // TODO: obtener email real del usuario logueado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        carritoManager = new CarritoManager(this);
        cartItems = carritoManager.obtenerCarrito(usuarioEmail);

        totalText = findViewById(R.id.textTotal);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(cartItems, this, carritoManager, usuarioEmail);
        recyclerView.setAdapter(adapter);

        Button btnComprar = findViewById(R.id.btnComprar);
        btnComprar.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, CheckoutActivity.class);
                startActivity(intent);
            }
        });

        updateTotal();
    }

    private void updateTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrecio() * item.getCantidad();
        }
        totalText.setText("Total: $" + String.format("%.2f", total));
    }

    @Override
    public void onQuantityChanged() {
        cartItems = carritoManager.obtenerCarrito(usuarioEmail);
        adapter.updateCartItems(cartItems);
        updateTotal();
    }

    @Override
    public void onItemRemoved(int position) {
        cartItems = carritoManager.obtenerCarrito(usuarioEmail);
        adapter.updateCartItems(cartItems);
        updateTotal();
    }
}
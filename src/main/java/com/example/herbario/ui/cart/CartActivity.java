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
import com.example.herbario.ui.checkout.CheckoutActivity;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartChangeListener {
    public static List<CartItem> cartItems = new ArrayList<>();
    private CartAdapter adapter;
    private TextView totalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        totalText = findViewById(R.id.textTotal);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(cartItems, this);
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
        updateTotal();
    }

    @Override
    public void onItemRemoved(int position) {
        updateTotal();
    }
}
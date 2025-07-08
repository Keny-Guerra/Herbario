package com.example.herbario.ui.checkout;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.herbario.R;
import com.example.herbario.data.CartItem;
import com.example.herbario.ui.cart.CartActivity;
import java.util.ArrayList;
import java.util.List;
import com.example.herbario.data.Compra;
import com.example.herbario.ui.history.HistoryActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {
    private TextView textTotal, textMetodoPago;
    private Button btnSeleccionarPago, btnConfirmarCompra;
    private List<CartItem> itemsCompra;
    private String metodoPago = "Efectivo"; // Por defecto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        itemsCompra = CartActivity.cartItems;

        textTotal = findViewById(R.id.textCheckoutTotal);
        textMetodoPago = findViewById(R.id.textMetodoPago);
        btnSeleccionarPago = findViewById(R.id.btnSeleccionarPago);
        btnConfirmarCompra = findViewById(R.id.btnConfirmarCompra);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCheckout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CheckoutAdapter adapter = new CheckoutAdapter(itemsCompra);
        recyclerView.setAdapter(adapter);

        textMetodoPago.setText("Método de pago: " + metodoPago);
        textTotal.setText("Total: $" + String.format("%.2f", calcularTotal()));

        btnSeleccionarPago.setOnClickListener(v -> mostrarDialogoMetodoPago());
        btnConfirmarCompra.setOnClickListener(v -> confirmarCompra());
    }

    private double calcularTotal() {
        double total = 0;
        for (CartItem item : itemsCompra) {
            total += item.getPrecio() * item.getCantidad();
        }
        return total;
    }

    private void mostrarDialogoMetodoPago() {
        final String[] metodos = {"Efectivo", "Tarjeta", "Transferencia"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona método de pago")
                .setSingleChoiceItems(metodos, -1, (dialog, which) -> {
                    metodoPago = metodos[which];
                })
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    textMetodoPago.setText("Método de pago: " + metodoPago);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void confirmarCompra() {
        if (!itemsCompra.isEmpty()) {
            String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
            List<CartItem> productosCopia = new ArrayList<>();
            for (CartItem item : itemsCompra) {
                productosCopia.add(new CartItem(item.getNombre(), item.getPrecio(), item.getCantidad(), item.getImagenResId()));
            }
            double total = calcularTotal();
            Compra compra = new Compra(fecha, productosCopia, total);
            HistoryActivity.historialCompras.add(compra);
            mostrarDialogoCalificacion(compra);
        } else {
            Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
        }
        itemsCompra.clear();
    }

    private void mostrarDialogoCalificacion(Compra compra) {
        if (compra.getProductos().isEmpty()) {
            finish();
            return;
        }
        CartItem producto = compra.getProductos().get(0);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Califica tu compra: " + producto.getNombre());
        final android.widget.EditText inputComentario = new android.widget.EditText(this);
        inputComentario.setHint("Comentario (opcional)");
        final android.widget.RatingBar ratingBar = new android.widget.RatingBar(this);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(1);
        ratingBar.setRating(5); // Valor por defecto: 5 estrellas
        ratingBar.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
            android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
            android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
        // Aumentar el tamaño visual del RatingBar
        ratingBar.setScaleX(1.5f);
        ratingBar.setScaleY(1.5f);
        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(32, 16, 32, 0);
        layout.addView(ratingBar);
        layout.addView(inputComentario);
        builder.setView(layout);
        builder.setPositiveButton("Enviar", (dialog, which) -> {
            float estrellas = ratingBar.getRating();
            if (estrellas < 1) estrellas = 1; // Forzar mínimo 1 estrella
            String comentario = inputComentario.getText().toString();
            compra.addCalificacion(new com.example.herbario.data.Calificacion(producto.getNombre(), "Usuario", estrellas, comentario));
            Toast.makeText(this, "¡Compra y calificación guardadas!", Toast.LENGTH_LONG).show();
            finish();
        });
        builder.setNegativeButton("Omitir", (dialog, which) -> {
            Toast.makeText(this, "¡Compra guardada!", Toast.LENGTH_LONG).show();
            finish();
        });
        builder.setCancelable(false);
        builder.show();
    }
}
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
import com.example.herbario.data.NotificacionManager;
import com.example.herbario.data.CarritoManager;
import com.example.herbario.data.CompraManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {
    private TextView textTotal, textMetodoPago;
    private Button btnSeleccionarPago, btnConfirmarCompra;
    private List<CartItem> itemsCompra;
    private String metodoPago = "Efectivo"; // Por defecto
    private CompraManager compraManager;
    private CarritoManager carritoManager;
    private String usuarioEmail = "usuario@ejemplo.com"; // TODO: obtener email real del usuario logueado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        compraManager = new CompraManager(this);
        carritoManager = new CarritoManager(this);
        itemsCompra = carritoManager.obtenerCarrito(usuarioEmail);

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
        
        // Mostrar información de depuración
        Toast.makeText(this, "Items en carrito: " + itemsCompra.size(), Toast.LENGTH_SHORT).show();
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
            double total = calcularTotal();
            
            try {
                // Verificar compras antes de guardar
                compraManager.verificarCompras();
                
                // Guarda la compra y su detalle en la base de datos
                boolean compraGuardada = compraManager.guardarCompra(usuarioEmail, fecha, total, itemsCompra);
                
                if (compraGuardada) {
                    // Verificar compras después de guardar
                    compraManager.verificarCompras();
                    
                    // Limpia el carrito después de la compra
                    carritoManager.limpiarCarrito(usuarioEmail);
                    
                    Toast.makeText(this, "¡Compra realizada exitosamente! Total: $" + String.format("%.2f", total), Toast.LENGTH_LONG).show();
                    
                    // Guarda notificación de compra exitosa
                    NotificacionManager notiManager = new NotificacionManager(this);
                    notiManager.guardarNotificacion(
                            usuarioEmail,
                            "¡Gracias por tu compra!",
                            "Tu compra por $" + String.format("%.2f", total) + " ha sido registrada. No olvides revisar el estado de tus plantas medicinales.",
                            new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date())
                    );
                    
                    finish();
                } else {
                    Toast.makeText(this, "Error: No se pudo guardar la compra", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error al guardar la compra: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
        }
    }
}
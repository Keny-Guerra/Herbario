package com.example.herbario.ui.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.herbario.R;
import com.example.herbario.data.CartItem;
import com.example.herbario.data.CarritoManager;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    private OnCartChangeListener listener;
    private CarritoManager carritoManager;
    private String usuarioEmail;

    public interface OnCartChangeListener {
        void onQuantityChanged();
        void onItemRemoved(int position);
    }

    public CartAdapter(List<CartItem> cartItems, OnCartChangeListener listener, CarritoManager carritoManager, String usuarioEmail) {
        this.cartItems = cartItems;
        this.listener = listener;
        this.carritoManager = carritoManager;
        this.usuarioEmail = usuarioEmail;
    }

    public void updateCartItems(List<CartItem> newItems) {
        this.cartItems = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.nombre.setText(item.getNombre());
        holder.precio.setText("$" + item.getPrecio());
        holder.cantidad.setText(String.valueOf(item.getCantidad()));
        
        // Verificar si el drawable existe antes de usarlo
        try {
            holder.imagen.setImageResource(item.getImagenResId());
        } catch (Exception e) {
            // Si el drawable no existe, usar un drawable por defecto
            holder.imagen.setImageResource(R.drawable.ic_launcher_foreground);
        }

        holder.btnSumar.setOnClickListener(v -> {
            int nuevaCantidad = item.getCantidad() + 1;
            carritoManager.agregarAlCarrito(usuarioEmail, position, item.getNombre(), item.getPrecio(), nuevaCantidad, item.getImagenResId());
            listener.onQuantityChanged();
        });

        holder.btnRestar.setOnClickListener(v -> {
            if (item.getCantidad() > 1) {
                int nuevaCantidad = item.getCantidad() - 1;
                carritoManager.agregarAlCarrito(usuarioEmail, position, item.getNombre(), item.getPrecio(), nuevaCantidad, item.getImagenResId());
                listener.onQuantityChanged();
            }
        });

        holder.btnEliminar.setOnClickListener(v -> {
            // Eliminar el producto del carrito en la base de datos
            carritoManager.limpiarCarrito(usuarioEmail); // Si tienes método para eliminar solo un producto, úsalo aquí
            listener.onItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView nombre, precio, cantidad;
        ImageButton btnSumar, btnRestar, btnEliminar;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imageCartPlanta);
            nombre = itemView.findViewById(R.id.textCartNombre);
            precio = itemView.findViewById(R.id.textCartPrecio);
            cantidad = itemView.findViewById(R.id.textCartCantidad);
            btnSumar = itemView.findViewById(R.id.btnSumar);
            btnRestar = itemView.findViewById(R.id.btnRestar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
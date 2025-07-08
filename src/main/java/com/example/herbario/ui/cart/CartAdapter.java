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
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    private OnCartChangeListener listener;

    public interface OnCartChangeListener {
        void onQuantityChanged();
        void onItemRemoved(int position);
    }

    public CartAdapter(List<CartItem> cartItems, OnCartChangeListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
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
        holder.imagen.setImageResource(item.getImagenResId());

        holder.btnSumar.setOnClickListener(v -> {
            item.setCantidad(item.getCantidad() + 1);
            notifyItemChanged(position);
            listener.onQuantityChanged();
        });

        holder.btnRestar.setOnClickListener(v -> {
            if (item.getCantidad() > 1) {
                item.setCantidad(item.getCantidad() - 1);
                notifyItemChanged(position);
                listener.onQuantityChanged();
            }
        });

        holder.btnEliminar.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyItemRemoved(position);
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
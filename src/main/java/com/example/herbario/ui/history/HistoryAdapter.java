package com.example.herbario.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.herbario.R;
import com.example.herbario.data.Compra;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<Compra> compras;

    public HistoryAdapter(List<Compra> compras) {
        this.compras = compras;
    }

    public void updateCompras(List<Compra> newCompras) {
        this.compras = newCompras;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_compra, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Compra compra = compras.get(position);
        holder.fecha.setText("Fecha: " + compra.getFecha());
        holder.total.setText("Total: $" + String.format("%.2f", compra.getTotal()));

        // Mostrar productos como texto simple
        StringBuilder productos = new StringBuilder();
        if (compra.getProductos() != null && !compra.getProductos().isEmpty()) {
            for (int i = 0; i < compra.getProductos().size(); i++) {
                productos.append(compra.getProductos().get(i).getNombre());
                if (i < compra.getProductos().size() - 1) productos.append(", ");
            }
            holder.productos.setText("Productos: " + productos.toString());
        } else {
            holder.productos.setText("Productos: No disponibles");
        }
        
        // Ocultar el TextView de calificaciones ya que no lo estamos usando
        if (holder.calificaciones != null) {
            holder.calificaciones.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return compras != null ? compras.size() : 0;
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView fecha, productos, total, calificaciones;

        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.textFechaCompra);
            productos = itemView.findViewById(R.id.textProductosCompra);
            total = itemView.findViewById(R.id.textTotalCompra);
            calificaciones = itemView.findViewById(R.id.textCalificacionesCompra);
        }
    }
}
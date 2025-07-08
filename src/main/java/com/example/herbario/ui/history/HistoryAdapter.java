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

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_compra, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Compra compra = compras.get(position);
        holder.fecha.setText(compra.getFecha());
        holder.total.setText("Total: $" + String.format("%.2f", compra.getTotal()));

        // Mostrar productos como texto simple
        StringBuilder productos = new StringBuilder();
        for (int i = 0; i < compra.getProductos().size(); i++) {
            productos.append(compra.getProductos().get(i).getNombre());
            if (i < compra.getProductos().size() - 1) productos.append(", ");
        }
        holder.productos.setText("Productos: " + productos.toString());

        // Mostrar calificaciones si existen
        StringBuilder calificaciones = new StringBuilder();
        if (compra.getCalificaciones() != null && !compra.getCalificaciones().isEmpty()) {
            calificaciones.append("Calificaciones:\n");
            for (int i = 0; i < compra.getCalificaciones().size(); i++) {
                calificaciones.append("- ")
                    .append(compra.getCalificaciones().get(i).getProducto())
                    .append(": ")
                    .append(compra.getCalificaciones().get(i).getEstrellas())
                    .append(" estrellas. ")
                    .append(compra.getCalificaciones().get(i).getComentario());
                if (i < compra.getCalificaciones().size() - 1) calificaciones.append("\n");
            }
        } else {
            calificaciones.append("Sin calificaciones");
        }
        holder.calificaciones.setText(calificaciones.toString());
    }

    @Override
    public int getItemCount() {
        return compras.size();
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
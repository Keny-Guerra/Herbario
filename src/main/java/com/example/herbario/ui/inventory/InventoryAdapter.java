package com.example.herbario.ui.inventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.herbario.R;
import com.example.herbario.data.Planta;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    private List<Planta> plantas;
    private OnPlantaClickListener listener;

    public interface OnPlantaClickListener {
        void onPlantaClick(Planta planta);
    }

    public InventoryAdapter(List<Planta> plantas, OnPlantaClickListener listener) {
        this.plantas = plantas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_planta, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Planta planta = plantas.get(position);
        holder.nombre.setText(planta.getNombre());
        holder.descripcion.setText(planta.getDescripcion());
        holder.cantidad.setText("Cantidad: " + planta.getCantidad());
        holder.precio.setText("$" + String.format("%.2f", planta.getPrecio()));
        holder.itemView.setOnClickListener(v -> listener.onPlantaClick(planta));
    }

    @Override
    public int getItemCount() {
        return plantas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, descripcion, cantidad, precio;
        ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.textNombrePlanta);
            descripcion = itemView.findViewById(R.id.textDescripcionPlanta);
            cantidad = itemView.findViewById(R.id.textCantidadPlanta);
            precio = itemView.findViewById(R.id.textPrecioPlanta);
        }
    }
}
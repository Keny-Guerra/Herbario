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

    public void updatePlantas(List<Planta> newPlantas) {
        this.plantas = newPlantas;
        notifyDataSetChanged();
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
        
        // Verificar si el drawable existe antes de usarlo
        try {
            holder.imagen.setImageResource(planta.getImagenResId());
        } catch (Exception e) {
            // Si el drawable no existe, usar un drawable por defecto
            holder.imagen.setImageResource(R.drawable.ic_launcher_foreground);
        }
        
        holder.itemView.setOnClickListener(v -> listener.onPlantaClick(planta));
    }

    @Override
    public int getItemCount() {
        return plantas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, descripcion, cantidad, precio;
        android.widget.ImageView imagen;
        
        ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.textNombrePlanta);
            descripcion = itemView.findViewById(R.id.textDescripcionPlanta);
            cantidad = itemView.findViewById(R.id.textCantidadPlanta);
            precio = itemView.findViewById(R.id.textPrecioPlanta);
            imagen = itemView.findViewById(R.id.imagePlanta);
        }
    }
}
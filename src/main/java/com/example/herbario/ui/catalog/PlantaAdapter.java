package com.example.herbario.ui.catalog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.herbario.R;
import com.example.herbario.data.Planta;
import java.util.List;

public class PlantaAdapter extends RecyclerView.Adapter<PlantaAdapter.PlantaViewHolder> {
    private List<Planta> plantas;
    private OnPlantaClickListener listener;

    public interface OnPlantaClickListener {
        void onPlantaClick(Planta planta);
    }

    public PlantaAdapter(List<Planta> plantas, OnPlantaClickListener listener) {
        this.plantas = plantas;
        this.listener = listener;
    }

    public void updatePlantas(List<Planta> newPlantas) {
        this.plantas = newPlantas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlantaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_planta, parent, false);
        return new PlantaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantaViewHolder holder, int position) {
        Planta planta = plantas.get(position);
        holder.nombre.setText(planta.getNombre());
        holder.descripcion.setText(planta.getDescripcion());
        holder.precio.setText("$" + String.format("%.2f", planta.getPrecio()));
        holder.cantidad.setText("Cantidad: " + planta.getCantidad());
        
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

    static class PlantaViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView nombre, descripcion, precio, cantidad;

        PlantaViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagePlanta);
            nombre = itemView.findViewById(R.id.textNombrePlanta);
            descripcion = itemView.findViewById(R.id.textDescripcionPlanta);
            precio = itemView.findViewById(R.id.textPrecioPlanta);
            cantidad = itemView.findViewById(R.id.textCantidadPlanta);
        }
    }
}
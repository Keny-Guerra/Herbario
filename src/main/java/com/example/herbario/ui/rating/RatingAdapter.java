package com.example.herbario.ui.rating;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.herbario.R;
import com.example.herbario.data.Calificacion;
import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingViewHolder> {
    private List<Calificacion> calificaciones;

    public RatingAdapter(List<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }

    @NonNull
    @Override
    public RatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calificacion, parent, false);
        return new RatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingViewHolder holder, int position) {
        Calificacion cal = calificaciones.get(position);
        holder.textUsuario.setText("Usuario: " + cal.getUsuario());
        holder.textComentario.setText(cal.getComentario());
        holder.ratingBar.setRating(cal.getEstrellas());
        holder.textProducto.setText("Producto: " + cal.getProducto());
    }

    @Override
    public int getItemCount() {
        return calificaciones.size();
    }

    static class RatingViewHolder extends RecyclerView.ViewHolder {
        TextView textUsuario, textComentario, textProducto;
        RatingBar ratingBar;

        RatingViewHolder(@NonNull View itemView) {
            super(itemView);
            textUsuario = itemView.findViewById(R.id.textUsuario);
            textComentario = itemView.findViewById(R.id.textComentario);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            textProducto = itemView.findViewById(R.id.textProducto);
        }
    }
}
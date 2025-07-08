package com.example.herbario.ui.rating;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.herbario.R;
import com.example.herbario.data.Calificacion;
import java.util.ArrayList;
import java.util.List;

public class RatingActivity extends AppCompatActivity {
    public static List<Calificacion> calificaciones = new ArrayList<>();
    private RatingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewRatings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RatingAdapter(calificaciones);
        recyclerView.setAdapter(adapter);

        EditText editUsuario = findViewById(R.id.editUsuario);
        EditText editProducto = findViewById(R.id.editProducto);
        EditText editComentario = findViewById(R.id.editComentario);
        RatingBar ratingBarInput = findViewById(R.id.ratingBarInput);
        Button btnEnviar = findViewById(R.id.btnEnviarCalificacion);

        btnEnviar.setOnClickListener(v -> {
            String usuario = editUsuario.getText().toString().trim();
            String producto = editProducto.getText().toString().trim();
            String comentario = editComentario.getText().toString().trim();
            float estrellas = ratingBarInput.getRating();

            if (usuario.isEmpty() || producto.isEmpty() || estrellas == 0) {
                Toast.makeText(this, "Completa usuario, producto y estrellas", Toast.LENGTH_SHORT).show();
                return;
            }

            Calificacion cal = new Calificacion(producto, usuario, estrellas, comentario);
            calificaciones.add(cal);
            adapter.notifyItemInserted(calificaciones.size() - 1);

            editUsuario.setText("");
            editProducto.setText("");
            editComentario.setText("");
            ratingBarInput.setRating(0);
            Toast.makeText(this, "¡Gracias por tu calificación!", Toast.LENGTH_SHORT).show();
        });
    }
}
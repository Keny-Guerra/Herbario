package com.example.herbario.ui.history;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.herbario.R;
import com.example.herbario.data.Compra;
import com.example.herbario.data.CompraManager;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private CompraManager compraManager;
    private HistoryAdapter adapter;
    private String usuarioEmail = "usuario@ejemplo.com"; // Reemplaza por el email real del usuario logueado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        compraManager = new CompraManager(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtén el historial de compras desde la base de datos
        List<Compra> historial = compraManager.obtenerHistorial(usuarioEmail);

        adapter = new HistoryAdapter(historial);
        recyclerView.setAdapter(adapter);
        
        // Mostrar mensaje si no hay compras
        TextView textNoCompras = findViewById(R.id.textNoCompras);
        if (textNoCompras != null) {
            if (historial.isEmpty()) {
                textNoCompras.setVisibility(TextView.VISIBLE);
                textNoCompras.setText("No tienes compras registradas");
            } else {
                textNoCompras.setVisibility(TextView.GONE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Verificar compras en la base de datos
        compraManager.verificarCompras();
        
        // Actualizar el historial cuando se regrese a esta actividad
        List<Compra> historial = compraManager.obtenerHistorial(usuarioEmail);
        if (adapter != null) {
            adapter.updateCompras(historial);
        }
        
        // Mostrar mensaje si no hay compras
        TextView textNoCompras = findViewById(R.id.textNoCompras);
        if (textNoCompras != null) {
            if (historial.isEmpty()) {
                textNoCompras.setVisibility(TextView.VISIBLE);
                textNoCompras.setText("No tienes compras registradas");
            } else {
                textNoCompras.setVisibility(TextView.GONE);
            }
        }
    }
}
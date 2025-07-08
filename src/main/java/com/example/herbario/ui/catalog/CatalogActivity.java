package com.example.herbario.ui.catalog;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.herbario.R;
import com.example.herbario.data.Planta;
import com.example.herbario.data.ProductoManager;
import com.example.herbario.ui.detail.PlantaDetailActivity;
import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity implements PlantaAdapter.OnPlantaClickListener {
    private List<Planta> plantasOriginales;
    private List<Planta> plantasFiltradas;
    private PlantaAdapter adapter;
    private ProductoManager productoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        productoManager = new ProductoManager(this);
        productoManager.inicializarProductosEjemplo(); // Solo si está vacío
        
        // Obtener todas las plantas
        plantasOriginales = productoManager.obtenerTodosLosProductos();
        plantasFiltradas = new ArrayList<>(plantasOriginales);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCatalog);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlantaAdapter(plantasFiltradas, this);
        recyclerView.setAdapter(adapter);

        EditText editTextBuscar = findViewById(R.id.editTextBuscar);
        editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarPlantas(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void filtrarPlantas(String texto) {
        plantasFiltradas.clear();
        
        if (texto == null || texto.trim().isEmpty()) {
            // Si no hay texto de búsqueda, mostrar todas las plantas
            plantasFiltradas.addAll(plantasOriginales);
        } else {
            // Filtrar plantas que contengan el texto en nombre o descripción
            String textoBusqueda = texto.toLowerCase().trim();
            for (Planta planta : plantasOriginales) {
                if (planta.getNombre().toLowerCase().contains(textoBusqueda) ||
                    planta.getDescripcion().toLowerCase().contains(textoBusqueda)) {
                    plantasFiltradas.add(planta);
                }
            }
        }
        
        adapter.updatePlantas(plantasFiltradas);
    }

    @Override
    public void onPlantaClick(Planta planta) {
        Intent intent = new Intent(this, PlantaDetailActivity.class);
        intent.putExtra("nombre", planta.getNombre());
        intent.putExtra("descripcion", planta.getDescripcion());
        intent.putExtra("precio", planta.getPrecio());
        intent.putExtra("imagenResId", planta.getImagenResId());
        intent.putExtra("stock", planta.getCantidad());
        startActivity(intent);
    }
}
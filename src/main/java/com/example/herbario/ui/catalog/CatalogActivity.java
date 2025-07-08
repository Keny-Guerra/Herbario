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
import com.example.herbario.ui.detail.PlantaDetailActivity;
import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity implements PlantaAdapter.OnPlantaClickListener {
    private List<Planta> plantas;
    private List<Planta> plantasFiltradas;
    private PlantaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        plantas = getPlantasEjemplo();
        plantasFiltradas = new ArrayList<>(plantas);

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
        if (texto.isEmpty()) {
            plantasFiltradas.addAll(plantas);
        } else {
            for (Planta planta : plantas) {
                if (planta.getNombre().toLowerCase().contains(texto.toLowerCase()) ||
                        planta.getDescripcion().toLowerCase().contains(texto.toLowerCase())) {
                    plantasFiltradas.add(planta);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private List<Planta> getPlantasEjemplo() {
        List<Planta> lista = new ArrayList<>();
        lista.add(new Planta("Manzanilla", "Alivia dolores estomacales", 10, 5.0, R.drawable.manzanilla));
        lista.add(new Planta("Menta", "Refrescante y digestiva", 15, 4.5, R.drawable.menta));
        lista.add(new Planta("Romero", "Aromática y medicinal", 8, 6.0, R.drawable.romero));
        lista.add(new Planta("Lavanda", "Relajante y aromática", 12, 7.0, R.drawable.lavanda));
        return lista;
    }

    @Override
    public void onPlantaClick(Planta planta) {
        Intent intent = new Intent(this, PlantaDetailActivity.class);
        intent.putExtra("nombre", planta.getNombre());
        intent.putExtra("descripcion", planta.getDescripcion());
        intent.putExtra("precio", planta.getPrecio());
        intent.putExtra("imagenResId", planta.getImagenResId());
        startActivity(intent);
    }
}
package com.example.herbario.ui.preferences;

import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.herbario.R;

public class PreferencesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        Switch switchDarkMode = findViewById(R.id.switchDarkMode);

        // Cargar preferencia guardada
        boolean darkMode = getSharedPreferences("prefs", MODE_PRIVATE).getBoolean("dark_mode", false);
        switchDarkMode.setChecked(darkMode);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            // Guardar preferencia
            getSharedPreferences("prefs", MODE_PRIVATE).edit().putBoolean("dark_mode", isChecked).apply();
        });
    }
}
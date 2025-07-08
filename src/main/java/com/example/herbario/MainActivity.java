package com.example.herbario;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.herbario.ui.auth.LoginActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Siempre redirige a login (no hay persistencia real)
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
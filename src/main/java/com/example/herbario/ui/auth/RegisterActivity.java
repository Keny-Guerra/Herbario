package com.example.herbario.ui.auth;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.herbario.R;
import com.example.herbario.data.AuthManager;

public class RegisterActivity extends AppCompatActivity {
    private AuthManager authManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authManager = new AuthManager(this);

        EditText email = findViewById(R.id.editTextEmail);
        EditText password = findViewById(R.id.editTextPassword);
        EditText nombre = findViewById(R.id.editTextNombre);
        Button registerBtn = findViewById(R.id.buttonRegister);

        registerBtn.setOnClickListener(v -> {
            String emailStr = email.getText().toString();
            String passwordStr = password.getText().toString();
            String nombreStr = nombre.getText().toString();
            if (emailStr.isEmpty() || passwordStr.isEmpty() || nombreStr.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else if (authManager.registerUser(emailStr, passwordStr, nombreStr)) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al registrar. ¿Ya existe ese usuario?", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
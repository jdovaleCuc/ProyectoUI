package com.example.proyectoui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private final String PASSWORD = "admin12345";
    public static final String EXTRA_USUARIO = "com.example.demo01.USUARIO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }

    //Metodo boton registrar
    public void registrarse(View view){
        Intent registrar = new Intent(this, RegistroActivity.class);
        startActivity(registrar);
    }

    public void ingresarUsuario(View view) {
        //Validación de blanco
        EditText edtNombre = findViewById(R.id.edtNombreUsuario);
        EditText edtPassword = findViewById(R.id.edtPassword);
        String textoNombre = edtNombre.getText().toString();
        String textoPassword = edtPassword.getText().toString();
        if (textoNombre.isEmpty()) {
            edtNombre.setError("El nombre está en blanco!");
        }
        else {
            if (textoPassword.isEmpty()) {
                edtPassword.setError("La contraseña está en blanco!");
            }
            else {
                Toast.makeText(this, "Ingreso exitoso!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
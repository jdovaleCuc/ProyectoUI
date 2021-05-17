package com.example.proyectoui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private final String PASSWORD = "admin12345";
    public static final String EXTRA_USUARIO = "com.example.demo01.USUARIO";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
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
        String email = edtNombre.getText().toString();
        String password = edtPassword.getText().toString();
        if (email.isEmpty()) {
            edtNombre.setError("El nombre está en blanco!");
        }
        else {
            if (password.isEmpty()) {
                edtPassword.setError("La contraseña está en blanco!");
            }
            else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("TAC", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                   // updateUI(user);
                                }else {
                                    Log.w("TAG", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }
}
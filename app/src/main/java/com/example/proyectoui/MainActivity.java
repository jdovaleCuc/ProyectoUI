package com.example.proyectoui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.proyectoui.adaptador.RecyclerAdapter;
import com.example.proyectoui.modelo.HousesList;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.Toolbar_main);
        BottomNavigationView navigationView = findViewById(R.id.Botton_Nav);
        FragmentHome fragmentHome = new FragmentHome();
        FragmentNotification fragmentNotification = new FragmentNotification();
        AccountFragment accountFragment = new AccountFragment();
        setSupportActionBar(toolbar);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.inicio) {
                    SetFragment(fragmentHome);
                    return true;
                } else if (id == R.id.notificacion) {
                    SetFragment(fragmentNotification);
                    return true;
                } else if (id == R.id.cuenta) {
                    SetFragment(accountFragment);
                    return true;
                }
                return false;
            }
        });
        navigationView.setSelectedItemId(R.id.inicio);
    }

    private void SetFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    public void AddPost(View view){
        Intent intent = new Intent(this,NewPostActivity.class);
        startActivity(intent);
    }





}
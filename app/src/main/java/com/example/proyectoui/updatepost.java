package com.example.proyectoui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.proyectoui.modelo.HousesList;

public class updatepost extends AppCompatActivity {

    private TextView title, desc;
    private byte [] imgByte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepost);
        Toolbar toolbar = findViewById(R.id.Toolbar_update);
        setSupportActionBar(toolbar);
        title = findViewById(R.id.updateTitle);
        desc = findViewById(R.id.updateDesc);
        initValues();
    }

    private void initValues(){
        HousesList houseDetail = (HousesList) getIntent().getExtras().getSerializable("house_update");
        title.setText(houseDetail.getTitle());
        desc.setText(houseDetail.getDescription());
    }
}
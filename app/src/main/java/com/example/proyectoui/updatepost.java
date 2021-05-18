package com.example.proyectoui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectoui.DataBase.DatabaseHelper;
import com.example.proyectoui.modelo.HousesList;

public class updatepost extends AppCompatActivity {
    private HousesList houseDetail;
    private TextView title, desc;
    private byte [] imgByte;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepost);
        Toolbar toolbar = findViewById(R.id.Toolbar_update);
        setSupportActionBar(toolbar);
        title = findViewById(R.id.updateTitle);
        desc = findViewById(R.id.updateDesc);
        update = findViewById(R.id.updatePost);
        final DatabaseHelper BD = new DatabaseHelper(this);
        initValues();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //BD.updatePension(houseDetail.getId(),title.getText().toString(),desc.getText().toString(),houseDetail.getImgResource(),houseDetail.getUbicacion(),houseDetail.getTelefono());
            }
        });
    }

    private void initValues(){
        houseDetail = (HousesList) getIntent().getExtras().getSerializable("house_update");
        title.setText(houseDetail.getTitle());
        desc.setText(houseDetail.getDescription());
    }
}
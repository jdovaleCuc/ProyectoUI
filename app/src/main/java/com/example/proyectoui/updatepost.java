package com.example.proyectoui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoui.DataBase.DatabaseHelper;
import com.example.proyectoui.modelo.HousesList;

public class updatepost extends AppCompatActivity {
    private HousesList houseDetail;
    private TextView title, desc;
    private ImageView image;
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
        image = findViewById(R.id.ImagenPost);
        update = findViewById(R.id.updatePost);
        final DatabaseHelper BD = new DatabaseHelper(this);
        initValues();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BD.updatePension(houseDetail.getId(),title.getText().toString(),desc.getText().toString(),houseDetail.getUbicacion(),houseDetail.getTelefono());
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                Toast.makeText(updatepost.this, "Publicacion Actualizada Correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void initValues(){
        houseDetail = (HousesList) getIntent().getExtras().getSerializable("house_update");
        byte[] imgTemp = houseDetail.getImgResource();
        Bitmap bt = BitmapFactory.decodeByteArray(imgTemp,0,imgTemp.length);
        image.setImageBitmap(bt);
        title.setText(houseDetail.getTitle());
        desc.setText(houseDetail.getDescription());
    }
}
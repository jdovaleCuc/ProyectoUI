package com.example.proyectoui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoui.DataBase.DatabaseHelper;
import com.example.proyectoui.modelo.HousesList;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ImageView imgDetail;
    private TextView  TitleDetail;
    private TextView  DescriptionDetail;
    private HousesList houseDetail;
    private MapView mapView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle(getClass().getSimpleName());
        toolbar = findViewById(R.id.Toolbar_detail);
        setSupportActionBar(toolbar);
        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync( this);
        initViews();
        initValues();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.detail_icon_menu, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Eliminar Publicacion");
            builder.setMessage("¿Quieres eliminar esta publicacion?");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    deletePost();
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else if(id == R.id.update){
            Intent update = new Intent(this, updatepost.class);
            update.putExtra("house_update", houseDetail);
            startActivity(update);
        }
        return true;
    }

    public void deletePost(){
        final DatabaseHelper BD = new DatabaseHelper(this);
        BD.DeletePension(houseDetail.getId());
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Se ha eliminado la publicacion", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }


    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void initViews(){
        imgDetail = findViewById(R.id.ImgitemDetail);
        TitleDetail = findViewById(R.id.titledetail);
        DescriptionDetail = findViewById(R.id.descriptionDetail);
    }

    private void initValues(){
        houseDetail = (HousesList) getIntent().getExtras().getSerializable("HouseDetail");

        //imgDetail.setImageResource(houseDetail.getImgResource());
        TitleDetail.setText(houseDetail.getTitle());
        DescriptionDetail.setText(houseDetail.getDescription());
    }
   /* private int getImgRes(){
        houseDetail = (HousesList) getIntent().getExtras().getSerializable("HouseDetail");
        return houseDetail.getImgResource();
    }*/

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
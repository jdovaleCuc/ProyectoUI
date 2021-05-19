package com.example.proyectoui;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationManager;
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
import androidx.core.app.ActivityCompat;

import com.example.proyectoui.DataBase.DatabaseHelper;
import com.example.proyectoui.modelo.HousesList;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DetailActivity extends AppCompatActivity  {
    private FirebaseAuth mAuth;
    private ImageView imgDetail, imgPhone, imgEmail;
    private TextView TitleDetail;
    private TextView DescriptionDetail;
    private HousesList houseDetail;
    private MapView mapView;
    private FusedLocationProviderClient client;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mAuth = FirebaseAuth.getInstance();
        setTitle(getClass().getSimpleName());
        toolbar = findViewById(R.id.Toolbar_detail);
        setSupportActionBar(toolbar);

        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        //mapView.getMapAsync(this);
        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(DetailActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }else {
            ActivityCompat.requestPermissions(DetailActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }

        imgPhone = findViewById(R.id.phoneView);
        imgEmail = findViewById(R.id.EmailView);
        imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowInfo(houseDetail.getTitle(), "Telefono de Contacto: " + houseDetail.getTelefono());
            }
        });
        imgEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowInfo(houseDetail.getTitle(), "Email de Contacto: " + houseDetail.getIdUser());
            }
        });
        initViews();
        initValues();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                            MarkerOptions options = new MarkerOptions().position(latLng).title("Estoy Aqui");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            getMenuInflater().inflate(R.menu.detail_icon_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete) {
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
        } else if (id == R.id.update) {
            Intent update = new Intent(this, updatepost.class);
            update.putExtra("house_update", houseDetail);
            startActivity(update);
        }
        return true;
    }

    private void ShowInfo(String Referent ,String topic){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Referent);
        builder.setMessage(topic);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { dialogInterface.cancel();; }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void deletePost() {
        final DatabaseHelper BD = new DatabaseHelper(this);
        BD.DeletePension(houseDetail.getId());
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Se ha eliminado la publicacion", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }


  /*  public void onMapReady(GoogleMap map) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           return;
        }
        Location  location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
        map.addMarker(new MarkerOptions().position(pos).title("Marker"));
        map.moveCamera(CameraUpdateFactory.newLatLng(pos));
    }*/

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
        byte[] img = houseDetail.getImgResource() ;
        Bitmap bt = BitmapFactory.decodeByteArray(img,0,img.length);
        imgDetail.setImageBitmap(bt);
        TitleDetail.setText(houseDetail.getTitle());
        DescriptionDetail.setText(houseDetail.getDescription());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
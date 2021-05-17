package com.example.proyectoui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoui.DataBase.DatabaseHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class NewPostActivity extends AppCompatActivity {
    private EditText title,desc ;
    Button BtnAdd;
    private ImageButton btntomarfoto,btnsubirfoto;
    private ImageView img;
   // byte[] ByteImg;


    Uri imagenUri;

    int TOMAR_FOTO = 100;
    int SELEC_IMAGEN = 200;

    String CARPETA_RAIZ = "MisFotosApp";
    String CARPETA_IMAGENES = "imagenes";
    String RUTA_IMAGEN = CARPETA_RAIZ + CARPETA_IMAGENES;
    String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        img = findViewById(R.id.ImagenPost);
        btnsubirfoto = findViewById(R.id.UpPhoto);
        btntomarfoto = findViewById(R.id.CatchPhoto);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }

        btntomarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFoto();
            }
        });

        btnsubirfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subirFoto();
            }
        });

        title = findViewById(R.id.NewPostTitle);
        desc = findViewById(R.id.NewPostDesc);
        //img = findViewById(R.id.NewPostImg);
        BtnAdd = findViewById(R.id.AddPost);
        Toolbar toolbar = findViewById(R.id.Toolbar_post);
        setSupportActionBar(toolbar);
        final DatabaseHelper BD = new DatabaseHelper(this);
        BtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] BYTE = getImgByte();
                if (BYTE == null){
                    Toast.makeText(NewPostActivity.this, "Byte de imagen Nula", Toast.LENGTH_SHORT).show();
                }else{
                    BD.AgregarPension(title.getText().toString(),desc.getText().toString(),BYTE,"Malambo","3045933566");
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(NewPostActivity.this, "Se Publico Correctamente", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void tomarFoto(){
        String nombreImagen = "";
        File fileImagen = new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        Boolean isCreate = fileImagen.exists();

        if (isCreate == false){
            isCreate = fileImagen.mkdirs();
        }

        if (isCreate == true){
            nombreImagen = (System.currentTimeMillis() / 1000) + ".jpg";
        }

        path = Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombreImagen;
        File imagen = new File(path);

        Intent intent  =null;
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            String authorities = this.getPackageName()+".provider";
            Uri imageUri = FileProvider.getUriForFile(this,authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(imagen));
        }
        startActivityForResult(intent,TOMAR_FOTO);
    }

    public void subirFoto(){
        Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        galeria.setType("image/");
        startActivityForResult(galeria.createChooser(galeria, "Selecciona una imagen de referencia"), SELEC_IMAGEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELEC_IMAGEN){
            imagenUri = data.getData();
            img.setImageURI(imagenUri);
        }else if(resultCode == RESULT_OK && requestCode == TOMAR_FOTO){
            MediaScannerConnection.scanFile(this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String s, Uri uri) {

                }
            });
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            img.setImageBitmap(bitmap);

        }
    }

    public byte[] getImgByte() {

        //img.setDrawingCacheEnabled(true);
        //img.buildDrawingCache();
        Bitmap bmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmap.compress(Bitmap.CompressFormat.PNG, 100 , byteArrayOutputStream);
        byte[] ByteImg = byteArrayOutputStream.toByteArray();
        return ByteImg;

    }
}
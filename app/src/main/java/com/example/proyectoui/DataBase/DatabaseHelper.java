package com.example.proyectoui.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.proyectoui.modelo.HousesList;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_PENSION = "PENSION";

    public static final String _ID = "_id";
    public static final String TIT = "titulo";
    public static final String DESC = "description";
    public static final String UBI = "ubicacion";
    public static final String IMG = "imagen";
    public static final String TEL = "telefono";

    public static final String TABLE_USER = "USER";

    public static final String _ID_USER = "_id_user";
    private static final String USERNAME = "username";
    private static final String LASTNAME = "lastname";
    private static final String UNIVERSIDAD = "universidad";
    private static final String EMAIL = "useremail";

    static final String DB_NAME = "RESERVU.DB";

    static final int DB_VERSION = 1;

    private static final String CREATE_TABLE_PENSION = "create table " + TABLE_PENSION + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TIT + " TEXT NOT NULL, " + DESC + " TEXT, "+
            UBI + " TEXT , " + IMG + " BLOB , " + TEL + " TEXT, " + EMAIL + " TEXT, FOREIGN KEY("+EMAIL+") REFERENCES "+TABLE_USER+"("+EMAIL+"));";

    private static final String CREATE_TABLE_USER = "create table " + TABLE_USER + "(" + _ID_USER
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USERNAME + " TEXT NOT NULL, " + LASTNAME + " TEXT, "+
            UNIVERSIDAD + " TEXT , " + EMAIL + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_PENSION);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_PENSION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_USER);
        onCreate(sqLiteDatabase);
    }

    public void AgregarUser( String name , String lastname, String uni , String Email){
        SQLiteDatabase BD = getWritableDatabase();
        if (BD!=null){
            BD.execSQL("INSERT INTO USER(username, lastname, universidad, useremail) VALUES('"+name+"','"+lastname+"','"+uni+"','"+Email+"')");
            BD.close();
        }
    }

    public void AgregarPension( String titulo , String desc, byte[] img , String ubicacion , String telefono, String idUser){
        SQLiteDatabase BD = getWritableDatabase();
        if (BD!=null){
            BD.execSQL("INSERT INTO PENSION(titulo, description, ubicacion, imagen, telefono, useremail) VALUES('"+titulo+"','"+desc+"','"+ubicacion+"','"+img+"','"+telefono+"','"+idUser+"')");
            BD.close();
        }
    }

    public List<HousesList> Show(){
        SQLiteDatabase BD = getWritableDatabase();
        Cursor cursor = BD.rawQuery("SELECT * FROM PENSION",null);
        List<HousesList> Pensiones = new ArrayList<>();
        Bitmap bt = null;
        if (cursor.moveToFirst()){
            do {
                byte[] img = cursor.getBlob(4);
                bt = BitmapFactory.decodeByteArray(img,0,img.length);
                Pensiones.add(new HousesList(cursor.getInt(0),cursor.getString(6),cursor.getString(1),cursor.getString(2),cursor.getString(3),bt,cursor.getString(5)));
            }while (cursor.moveToNext());
        }
        return Pensiones;
    }

    public void DeletePension( int id){
        SQLiteDatabase BD = getWritableDatabase();
        if (BD!=null){
            BD.execSQL("DELETE FROM PENSION WHERE _id='"+id+"'");
            BD.close();
        }
    }

    public void updatePension(int id ,  String titulo , String desc, Bitmap img , String ubicacion , String telefono ){
        SQLiteDatabase BD = getWritableDatabase();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100 , byteArrayOutputStream);
        byte[] ByteImg = byteArrayOutputStream.toByteArray();
        if (BD!=null){
            BD.execSQL("UPDATE PENSION SET titulo='"+titulo+"',description='"+desc+"',ubicacionTEXT='"+ubicacion+"',imagen='"+ByteImg+"',telefono='"+telefono+"' WHERE _id='"+id+"'");
            BD.close();
        }
    }
}

package com.example.proyectoui.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.proyectoui.modelo.HousesList;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "PENSION";

    public static final String _ID = "_id";
    public static final String TIT = "titulo";
    public static final String DESC = "description";
    public static final String UBI = "ubicacion";
    public static final String IMG = "imagen";
    public static final String TEL = "telefono";

    static final String DB_NAME = "RESERVU.DB";

    static final int DB_VERSION = 2;

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TIT + " TEXT NOT NULL, " + DESC + " TEXT, "+
            UBI + "TEXT , " + IMG + " BLOB NOT NULL, " + TEL + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void AgregarPension( String titulo , String desc, byte[] img , String ubicacion , String telefono){
        SQLiteDatabase BD = getWritableDatabase();
        if (BD!=null){
            BD.execSQL("INSERT INTO PENSION VALUES('"+04+"','"+titulo+"','"+desc+"','"+ubicacion+"','"+img+"','"+telefono+"')");
            BD.close();
        }
    }

    public List<HousesList> Show(){
        SQLiteDatabase BD = getWritableDatabase();
        Cursor cursor = BD.rawQuery("SELECT * FROM PENSION",null);
        List<HousesList> Pensiones = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                Pensiones.add(new HousesList(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getBlob(4),cursor.getString(5)));
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

    public void updatePension(int id ,  String titulo , String desc, byte[] img , String ubicacion , String telefono ){
        SQLiteDatabase BD = getWritableDatabase();
        if (BD!=null){
            BD.execSQL("UPDATE PENSION SET titulo='"+titulo+"',description='"+desc+"',ubicacion='"+ubicacion+"',imagen='"+img+"',telefono='"+telefono+"' WHERE _id='"+id+"'");
            BD.close();
        }
    }
}

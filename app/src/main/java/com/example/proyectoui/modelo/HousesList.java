package com.example.proyectoui.modelo;

import android.graphics.Bitmap;

import java.io.Serializable;

public class HousesList implements Serializable {

    private int id;
    private String idUser;
    private String Title;
    private String Description;
    private String Ubicacion;
    private String Telefono;
    private Bitmap imgResource;

    public HousesList(int id,String idUser, String Title, String Description,String Ubicacion, Bitmap imgResource,String Telefono){
        this.id = id;
        this.idUser = idUser;
        this.Title = Title;
        this.Description = Description;
        this.imgResource = imgResource;
        this.Ubicacion= Ubicacion;
        this.Telefono= Telefono;
    }

    public int getId() { return id; }

    public String getTitle() {
        return Title;
    }

    public String getIdUser() { return idUser; }

    public String getDescription() {
        return Description;
    }

    public Bitmap getImgResource() {
        return imgResource;
    }

    public String getUbicacion() { return Ubicacion; }

    public String getTelefono() { return Telefono; }
}

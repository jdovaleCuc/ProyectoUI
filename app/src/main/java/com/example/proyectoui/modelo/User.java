package com.example.proyectoui.modelo;

public class User {

    private int id;
    private String username, lastname, univer, correo;

    public User(int id , String username, String lastname, String univer, String correo) {
        this.id = id;
        this.username = username;
        this.lastname = lastname;
        this.univer = univer;
        this.correo = correo;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUniver() {
        return univer;
    }

    public String getCorreo() {
        return correo;
    }
}

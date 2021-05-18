package com.example.proyectoui.modelo;

public class Notification {

    private int id_not;
    private String EmailUser;
    private String topic;
    private String DescriptionNot;

    public Notification(int id_not, String emailUser, String topic, String descriptionNot) {
        this.id_not = id_not;
        EmailUser = emailUser;
        this.topic = topic;
        DescriptionNot = descriptionNot;
    }

    public int getId_not() {
        return id_not;
    }

    public String getEmailUser() {
        return EmailUser;
    }

    public String getTopic() {
        return topic;
    }

    public String getDescriptionNot() {
        return DescriptionNot;
    }
}

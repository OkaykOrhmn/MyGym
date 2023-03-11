package com.example.mygym.database.models;

public class Days {
    private int id ;
    private String day;

    public Days() {
    }

    public Days(int id, String day) {
        this.id = id;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}

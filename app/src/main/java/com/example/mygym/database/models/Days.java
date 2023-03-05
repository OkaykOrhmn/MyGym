package com.example.mygym.database.models;

public class Days {
    private int id ;
    private String title;
    private String day;

    public Days() {
    }

    public Days(int id, String title, String day) {
        this.id = id;
        this.title = title;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}

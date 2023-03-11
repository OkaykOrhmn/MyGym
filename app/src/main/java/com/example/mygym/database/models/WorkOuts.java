package com.example.mygym.database.models;

public class WorkOuts {

    private int id;
    private String superId;
    private String day;
    private String type;
    private String typeId;
    private String titles;
    private String setType;
    private String setCount;
    private String moveCount;
    private String details;

    public WorkOuts() {
    }

    public WorkOuts(int id, String day, String titles, String details) {
        this.id = id;
        this.day = day;
        this.titles = titles;
        this.details = details;
    }

    public WorkOuts(int id, String day, String type, String typeId, String titles, String setType, String setCount, String moveCount, String details) {
        this.id = id;
        this.day = day;
        this.type = type;
        this.typeId = typeId;
        this.titles = titles;
        this.setType = setType;
        this.setCount = setCount;
        this.moveCount = moveCount;
        this.details = details;
    }

    public WorkOuts(String superId, String day, String titles, String setType, String setCount, String moveCount, String details) {
        this.superId = superId;
        this.day = day;
        this.titles = titles;

        this.setType = setType;
        this.setCount = setCount;
        this.moveCount = moveCount;
        this.details = details;
    }

    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getSetType() {
        return setType;
    }

    public void setSetType(String setType) {
        this.setType = setType;
    }

    public String getSetCount() {
        return setCount;
    }

    public void setSetCount(String setCount) {
        this.setCount = setCount;
    }

    public String getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(String moveCount) {
        this.moveCount = moveCount;
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

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
